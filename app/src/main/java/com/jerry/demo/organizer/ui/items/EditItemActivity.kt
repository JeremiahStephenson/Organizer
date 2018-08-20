package com.jerry.demo.organizer.ui.items

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.afollestad.materialdialogs.MaterialDialog
import com.bumptech.glide.Glide
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.ui.BaseActivity
import com.jerry.demo.organizer.util.tintAllIcons
import com.nguyenhoanglam.imagepicker.model.Config
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePickerActivity
import kotlinx.android.synthetic.main.activity_edit_item.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import java.util.*
import javax.inject.Inject

class EditItemActivity : BaseActivity() {

    @Inject
    lateinit var itemDao: ItemDao

    // keep a reference to this menu option so we can enable or disable it later
    private var deleteMenu: MenuItem? = null
    private var item: Item? = null

    private lateinit var viewModel: EditItemViewModel

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)
        setSupportActionBar(mainToolbar)

        // enable back arrow in toolbar
        supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        viewModel = ViewModelProviders.of(this).get(EditItemViewModel::class.java)

        // loads the item if it exists
        viewModel.item.observe(this, Observer { data ->
            data?.let {
                item = it
                showItemDetails(it)
            }
        })

        viewModel.imagePath?.let {
            Glide.with(this).load(viewModel.imagePath).into(itemImageView)
        }

        // set things up in the view if we're editing or creating
        intent.options {
            viewModel.setItemId(it.itemId ?: 0L)
            titleInputLayout.isHintAnimationEnabled = it.itemId == 0L
            descriptionInputLayout.isHintAnimationEnabled = it.itemId == 0L
            setTitle(when (it.itemId == 0L) { true -> R.string.new_item else -> R.string.edit_item })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_edit, menu)
        // tint all toolbar icons white
        menu?.tintAllIcons(this, android.R.color.white)
        deleteMenu = menu?.findItem(R.id.menu_item_delete)
        intent.options {
            deleteMenu?.isVisible = it.itemId != 0L
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            when (it.itemId) {
                R.id.menu_item_save -> saveItem()
                R.id.menu_item_delete -> confirmDeleteItem()
                R.id.menu_item_photo -> selectPhoto()
                android.R.id.home -> checkIfDifferent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        checkIfDifferent()
    }

    // called after the user has selected a photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Config.RC_PICK_IMAGES && resultCode == Activity.RESULT_OK) {
            val images = data?.getParcelableArrayListExtra<Parcelable>(Config.EXTRA_IMAGES)
            // show the photo and save the path so we can save it in the db later
            if (images?.isNotEmpty() == true && images.first() is Image) {
                viewModel.imagePath = (images.first() as Image).path
                Glide.with(this).load(viewModel.imagePath).into(itemImageView)
            }
        }
    }

    // check if the user changed data so we can ask them if they want to save it
    private fun checkIfDifferent() {
        when (isDifferent()) {
            true -> {
                MaterialDialog.Builder(this)
                        .title(R.string.save_changes)
                        .negativeText(R.string.no)
                        .positiveText(R.string.yes)
                        .onPositive { _, _ -> saveItem() }
                        .onNegative { _, _ -> finish() }
                        .show()
            }
            else -> finish()
        }
    }

    // displays the item's title, description, and photo if we're editing
    private fun showItemDetails(item: Item) {
        descriptionTextView.setText(item.description)
        titleEditText.setText(item.name)
        when {
            !viewModel.imagePath.isNullOrEmpty() -> item.imagePath = viewModel.imagePath ?: ""
            else -> viewModel.imagePath = item.imagePath
        }
        Glide.with(this).load(item.imagePath).into(itemImageView)
        titleInputLayout.isHintAnimationEnabled = true
        descriptionInputLayout.isHintAnimationEnabled = true
    }

    // checks if the user has changed something
    private fun isDifferent(): Boolean {
        when (item) {
            null -> return titleEditText.text.isNotEmpty() ||
                    descriptionTextView.text.isNotEmpty() || viewModel.imagePath?.isNotEmpty() ?: false
            else -> return item?.name != titleEditText.text.toString() ||
                    item?.description != descriptionTextView.text.toString() || item?.imagePath != viewModel.imagePath
        }
    }

    // performs form validation to make sure required fields have been filled out
    private fun validate(): Boolean {
        when (titleEditText.text.isEmpty()) {
            true -> {
                titleEditText.error = getString(R.string.title_required)
                return false
            }
            else -> titleEditText.setError(null)
        }

        when (descriptionTextView.text.isEmpty()) {
            true -> {
                descriptionTextView.error = getString(R.string.description_required)
                return false
            }
            else -> descriptionTextView.error = null
        }

        return true
    }

    // saves the item in a separate thread if it passes validation
    private fun saveItem() {
        if (!validate()) {
            return
        }
        launch(CommonPool) {
            itemDao.insert(Item().apply {
                name = titleEditText.text.toString()
                description = descriptionTextView.text.toString()
                imagePath = viewModel.imagePath ?: ""
                categoryId = intent.options { it.categoryId ?: 0L }
                timestamp = Calendar.getInstance().timeInMillis
                item?.let {
                    id = it.id
                    rating = it.rating
                }
            })
            finish()
        }
    }

    // confirm that the user wants to delete the item
    private fun confirmDeleteItem() {
        item?.let {
            MaterialDialog.Builder(this)
                    .title(getString(R.string.are_sure_delete, it.name))
                    .negativeText(R.string.cancel)
                    .positiveText(R.string.yes)
                    .onPositive { _, _ -> deleteItem() }
                    .show()
        }
    }

    // deletes the item in a separate thread
    private fun deleteItem() {
        item?.let {
            launch(CommonPool) {
                itemDao.deleteItem(it.id)
                finish()
            }
        }
    }

    // launches the photo selection ui
    private fun selectPhoto() {
        ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle(getString(R.string.folder))
                .setImageTitle(getString(R.string.tap_to_select))
                //.single()
                .setShowCamera(true)
                //.setImageDirectory(CAMERA_DIRECTORY)
                .start()
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, EditItemActivity::class) {
        val CAMERA_DIRECTORY = "Camera"
    }

    object IntentOptions {
        var Intent.itemId by IntentExtra.Long()
        var Intent.categoryId by IntentExtra.Long()
    }
}
