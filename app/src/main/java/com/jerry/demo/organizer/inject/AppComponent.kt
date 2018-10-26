package com.jerry.demo.organizer.inject

import com.jerry.demo.organizer.App
import com.jerry.demo.organizer.ui.BaseActivity
import com.jerry.demo.organizer.ui.BaseFragment
import com.jerry.demo.organizer.ui.category.CategoriesFragment
import com.jerry.demo.organizer.ui.category.CategoriesViewModel
import com.jerry.demo.organizer.ui.items.EditItemActivity
import com.jerry.demo.organizer.ui.items.EditItemViewModel
import com.jerry.demo.organizer.ui.items.ItemListFragment
import com.jerry.demo.organizer.ui.items.ItemListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(application: App)

    fun inject(target: BaseFragment)
    fun inject(target: CategoriesFragment)
    fun inject(target: ItemListFragment)
    fun inject(target: EditItemActivity)
    fun inject(target: BaseActivity)
}
