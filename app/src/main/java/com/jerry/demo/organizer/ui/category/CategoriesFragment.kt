package com.jerry.demo.organizer.ui.category

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.ui.BaseFragment
import com.jerry.demo.organizer.ui.GeneralActivity
import com.jerry.demo.organizer.ui.items.ItemListFragment
import kotlinx.android.synthetic.main.fragment_items.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class CategoriesFragment : BaseFragment() {

    private val categoryDao by inject<CategoryDao>()
    private val viewModel by viewModel<CategoriesViewModel>()

    private val categoriesAdapter by lazy {
        CategoriesAdapter().apply {
            itemClickListener = { category ->
                goToItemList(category.id)
            }
        }
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_items
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
                    .input(R.string.category, 0) { _, input ->
                        if (!input.isNullOrEmpty()) {
                            saveCategory(input.toString())
                        }
                    }.show()
        }
    }

    private fun saveCategory(category: String) {
        // save the category in a separate thread
        GlobalScope.launch(Dispatchers.Default) {
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
            GeneralActivity.start(it) { intent ->
                val bundle = Bundle()
                with(ItemListFragment.BundleOptions) {
                    bundle.categoryId = categoryId
                }
                intent.fragmentBundle = bundle
                intent.fragmentClass = ItemListFragment::class.java
            }
        }
    }
}
