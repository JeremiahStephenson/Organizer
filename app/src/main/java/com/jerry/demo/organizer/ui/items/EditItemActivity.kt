package com.jerry.demo.organizer.ui.items

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.util.tintAllIcons
import com.nguyenhoanglam.imagepicker.activity.ImagePicker
import com.nguyenhoanglam.imagepicker.activity.ImagePickerActivity
import com.nguyenhoanglam.imagepicker.model.Image
import kotlinx.android.synthetic.main.fragment_edit_item.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import javax.inject.Inject




class EditItemActivity : AppCompatActivity(), LifecycleRegistryOwner {
    private val lifecycleRegistry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return this.lifecycleRegistry
    }

    @Inject
    lateinit var itemDao: ItemDao

    private var catId: Long = 0L
    private var item: Item? = null
    private var deleteMenu: MenuItem? = null
    private var imgPath: String? = null
    private lateinit var viewModel: EditItemViewModel

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_edit_item)
        setSupportActionBar(mainToolbar)

        viewModel = ViewModelProviders.of(this).get(EditItemViewModel::class.java)

        viewModel.item.observe(this, Observer { data ->
            item = data
            showItemDetails()
        })

        intent.options {
            catId = it.categoryId ?: 0L
            viewModel.setItemId(it.itemId ?: 0L)
            titleInputLayout.isHintAnimationEnabled = it.itemId == 0L
            descriptionInputLayout.isHintAnimationEnabled = it.itemId == 0L
            setTitle(when (it.itemId == 0L) { true -> R.string.new_item else -> R.string.edit_item })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item_edit, menu)
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
                R.id.menu_item_delete -> deleteItem()
                R.id.menu_item_photo -> selectPhoto()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICKER && resultCode == Activity.RESULT_OK) {
            val images = data?.getParcelableArrayListExtra<Parcelable>(ImagePickerActivity.INTENT_EXTRA_SELECTED_IMAGES)
            if (images?.isNotEmpty() ?: false && images?.first() is Image) {
                imgPath = (images.first() as Image).path
                Glide.with(this).load(imgPath).into(itemImageView)
            }
        }
    }

    private fun showItemDetails() {
        item?.let {
            descriptionTextView.setText(it.description)
            titleEditText.setText(it.name)
            Glide.with(this).load(it.imagePath).into(itemImageView)
            imgPath = it.imagePath
            titleInputLayout.isHintAnimationEnabled = true
            descriptionInputLayout.isHintAnimationEnabled = true
        }
    }

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

    private fun saveItem() {
        launch(CommonPool) {
            when (item == null) {
                true -> {
                    itemDao.insert(Item().apply {
                        name = titleEditText.text.toString()
                        description = descriptionTextView.text.toString()
                        imagePath = imgPath ?: ""
                        categoryId = catId
                    })
                }
                else -> {
                    item?.let {
                        it.name = titleEditText.text.toString()
                        it.description = descriptionTextView.text.toString()
                        it.imagePath = imgPath ?: ""
                        itemDao.update(it)
                    }
                }
            }
            finish()
        }
    }

    private fun deleteItem() {
        launch(CommonPool) {
            item?.let {
                itemDao.deleteItem(it.id)
                finish()
            }
        }
    }

    private fun selectPhoto() {
        ImagePicker.create(this)
                .folderMode(true)
                .folderTitle(getString(R.string.folder))
                .imageTitle(getString(R.string.tap_to_select))
                .single()
                .showCamera(true)
                .imageDirectory(CAMERA_DIRECTORY)
                .start(REQUEST_CODE_PICKER);
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, EditItemActivity::class) {
        val REQUEST_CODE_PICKER = 300
        val CAMERA_DIRECTORY = "Camera"
    }

    object IntentOptions {
        var Intent.itemId by IntentExtra.Long()
        var Intent.categoryId by IntentExtra.Long()
    }
}
