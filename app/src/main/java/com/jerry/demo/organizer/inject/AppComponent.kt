package com.jerry.demo.organizer.inject


import com.jerry.demo.organizer.App
import com.jerry.demo.organizer.ui.category.CategoriesFragment
import com.jerry.demo.organizer.ui.category.CategoriesViewModel
import com.jerry.demo.organizer.ui.items.ItemListFragment
import com.jerry.demo.organizer.ui.items.ItemListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(application: App)

    fun inject(target: CategoriesFragment)
    fun inject(target: ItemListFragment)

    fun inject(target: CategoriesViewModel)
    fun inject(target: ItemListViewModel)
}
