package com.jerry.demo.organizer.ui.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.inject.Injector
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var categoryDao: CategoryDao

    var categories: LiveData<List<Category>>

    init {
        Injector.get().inject(this)
        categories = categoryDao.findAllLive()
    }
}
