package com.jerry.demo.organizer.inject

import com.jerry.demo.organizer.ui.category.CategoriesViewModel
import com.jerry.demo.organizer.ui.items.EditItemViewModel
import com.jerry.demo.organizer.ui.items.ItemListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    viewModel { ItemListViewModel(get(), get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { EditItemViewModel(get()) }
}
