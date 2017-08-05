package com.jerry.demo.organizer.ui.items

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.ui.fragment.BaseFragment
import com.jerry.demo.organizer.ui.widget.SpaceItemDecorator
import kotlinx.android.synthetic.main.fragment_items.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Long


class ItemListFragment : BaseFragment() {

    private var category: Category? = null
    private lateinit var viewModel: ItemListViewModel

    private val itemsAdapter by lazy {
        ItemsAdapter().apply {
            itemClickListener = { item ->
                goToItemEdit(item.id)
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
        viewModel = ViewModelProviders.of(this).get(ItemListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.category.observe(this, Observer { data ->
            data?.let {
                category = it
                setTitle(it.name)
            }
        })

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyTextView.text = getString(R.string.no_items)
        setupRecyclerView()
        btnNewItem.setOnClickListener {
            goToItemEdit()
        }
    }

    private fun setupRecyclerView() {
        itemsRecyclerView.layoutManager = LinearLayoutManager(activity)
        itemsRecyclerView.adapter = itemsAdapter
        itemsRecyclerView.addItemDecoration(SpaceItemDecorator(resources.getDimension(R.dimen.card_view_list_margin).toInt()))
    }

    private fun goToItemEdit(itemId: Long = 0L) {
        EditItemActivity.start(context) {
            it.itemId = itemId
            it.categoryId = category?.id ?: 0L
        }
    }

    object BundleOptions {
        var Bundle.categoryId by BundleExtra.Long(defaultValue = 0L)
    }
}
