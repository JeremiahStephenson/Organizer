package com.jerry.demo.organizer.ui.items

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.inject.Injector
import com.jerry.demo.organizer.ui.fragment.BaseFragment
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Long


class ItemListFragment : BaseFragment() {

    private lateinit var viewModel: ItemListViewModel

    init {
        Injector.get().inject(this)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_item_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ItemListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.category.observe(this, Observer { data ->
            data?.let { setTitle(it.name) }
        })

        with (BundleOptions) {
            arguments?.let {
                viewModel.setCategoryId(it.categoryId)
            }
        }
    }

    object BundleOptions {
        var Bundle.categoryId by BundleExtra.Long(defaultValue = 0L)
    }
}
