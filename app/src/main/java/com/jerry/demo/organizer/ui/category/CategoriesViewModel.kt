package com.jerry.demo.organizer.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import javax.inject.Inject

class CategoriesViewModel
@Inject constructor(categoryDao: CategoryDao) : ViewModel() {
    val categories: LiveData<List<Category>> = categoryDao.findAllLive()
}
