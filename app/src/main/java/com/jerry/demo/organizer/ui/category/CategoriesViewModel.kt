package com.jerry.demo.organizer.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao

class CategoriesViewModel(categoryDao: CategoryDao) : ViewModel() {
    val categories: LiveData<List<Category>> = categoryDao.findAllLive()
}
