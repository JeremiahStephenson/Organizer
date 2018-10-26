package com.jerry.demo.organizer.inject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jerry.demo.organizer.ui.category.CategoriesViewModel
import com.jerry.demo.organizer.ui.items.EditItemViewModel
import com.jerry.demo.organizer.ui.items.ItemListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ItemListViewModel::class)
    internal abstract fun bindItemListViewModel(itemListViewModel: ItemListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CategoriesViewModel::class)
    internal abstract fun bindCategoriesViewModel(categoriesViewModel: CategoriesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditItemViewModel::class)
    internal abstract fun bindEditItemViewModel(editItemViewModel: EditItemViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}