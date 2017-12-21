package com.jerry.demo.organizer.ui.items

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.database.item.ItemDao
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.ui.BaseFragment
import com.jerry.demo.organizer.ui.widget.SpaceItemDecorator
import kotlinx.android.synthetic.main.fragment_items.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Long
import javax.inject.Inject

class ItemListFragment : BaseFragment() {

    @Inject
    lateinit var itemDao: ItemDao
    @Inject
    lateinit var categoryDao: CategoryDao

    private var category: Category? = null
    private lateinit var viewModel: ItemListViewModel

    private val itemsAdapter by lazy {
        ItemsAdapter().apply {
            itemClickListener = { item ->
                activity?.let { goToItemEdit(it, item.id) }
            }
            onRatingClickListener = { item, rating ->
                saveItemRating(item.id, rating)
            }
        }
    }

    init {
        Injector.get().inject(this)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_items
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this).get(ItemListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // observe changes in the selected category
        viewModel.category.observe(this, Observer { data ->
            data?.let {
                category = it
                setTitle(it.name)
            }
        })

        // observe changes in the items
        viewModel.items.observe(this, Observer { data ->
            data?.let {
                itemsAdapter.data = it
                emptyTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        })

        with (BundleOptions) {
            arguments?.let {
                viewModel.setCategoryId(it.categoryId)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyTextView.text = getString(R.string.no_items)
        setupRecyclerView()
        btnNewItem.setOnClickListener {
            goToItemEdit(view.context)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_items, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_item_delete) {
            confirmDeleteCategory()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        // allow more columns if the screen is bigger
        val manager = StaggeredGridLayoutManager(resources.getInteger(R.integer.num_cols), StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        itemsRecyclerView.layoutManager = manager

        itemsRecyclerView.adapter = itemsAdapter
        // add spaces around the items since they're displayed in a cardview
        itemsRecyclerView.addItemDecoration(SpaceItemDecorator(resources.getDimension(R.dimen.card_view_list_margin).toInt()))
    }

    // show the item edit activity
    private fun goToItemEdit(context: Context, itemId: Long = 0L) {
        EditItemActivity.start(context) {
            it.itemId = itemId
            it.categoryId = category?.id ?: 0L
        }
    }

    private fun saveItemRating(itemId: Long, rating: Int) {
        launch(CommonPool) {
            itemDao.setItemRating(itemId, rating)
        }
    }

    // confirm that the user wants to delete the category
    private fun confirmDeleteCategory() {
        activity?.let {
            category?.let {
                MaterialDialog.Builder(activity!!)
                        .title(getString(R.string.are_sure_delete, it.name))
                        .negativeText(R.string.cancel)
                        .positiveText(R.string.yes)
                        .onPositive { _, _ -> deleteCategory() }
                        .show()
            }
        }
    }

    // delete the category in a separate thread
    private fun deleteCategory() {
        launch(CommonPool) {
            category?.let {
                categoryDao.deleteCategory(it.id)
                activity?.finish()
            }
        }
    }

    object BundleOptions {
        var Bundle.categoryId by BundleExtra.Long(defaultValue = 0L)
    }
}
