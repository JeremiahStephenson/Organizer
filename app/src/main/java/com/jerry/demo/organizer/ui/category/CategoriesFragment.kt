package com.jerry.demo.organizer.ui.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.ui.BaseFragment
import com.jerry.demo.organizer.ui.GeneralActivity
import com.jerry.demo.organizer.ui.items.ItemListFragment
import kotlinx.android.synthetic.main.fragment_items.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject

class CategoriesFragment : BaseFragment() {

    @Inject
    lateinit var categoryDao: CategoryDao

    private lateinit var viewModel: CategoriesViewModel

    private val categoriesAdapter by lazy {
        CategoriesAdapter().apply {
            itemClickListener = { category ->
                goToItemList(category.id)
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
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setTitle(R.string.categories)

        // observe changes in the list of categories
        viewModel.categories.observe(this, Observer { data ->
            data?.let {
                // pass the data to the adapter
                categoriesAdapter.data = it
                // if no data then show the empty text view
                emptyTextView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyTextView.text = getString(R.string.no_categories)
        setupRecyclerView()

        // show an edit text in a dialog for creating a new category
        btnNewItem.setOnClickListener {
            MaterialDialog.Builder(view.context)
                    .title(R.string.new_category)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input(R.string.category, 0, { _, input ->
                        if (!input.isNullOrEmpty()) {
                            saveCategory(input.toString())
                        }
                    }).show()
        }
    }

    private fun saveCategory(category: String) {
        // save the category in a separate thread
        val job = launch(CommonPool) {
            categoryDao.insert(Category().apply {
                name = category
                timestamp = Calendar.getInstance().timeInMillis
            })
        }
    }

    private fun setupRecyclerView() {
        itemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        itemsRecyclerView.adapter = categoriesAdapter
        itemsRecyclerView.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
    }

    private fun goToItemList(categoryId: Long) {
        // show the item list for the category
        context?.let {
            GeneralActivity.start(it) {
                val bundle: Bundle = Bundle()
                with(ItemListFragment.BundleOptions) {
                    bundle.categoryId = categoryId
                }
                it.fragmentBundle = bundle
                it.fragmentClass = ItemListFragment::class.java
            }
        }
    }
}
