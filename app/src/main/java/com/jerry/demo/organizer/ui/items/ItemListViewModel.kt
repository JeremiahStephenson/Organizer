package com.jerry.demo.organizer.ui.items

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.inject.Injector
import javax.inject.Inject

class ItemListViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var categoryDao: CategoryDao

    private val categoryId = MutableLiveData<Long>()

    var category: LiveData<Category>

    init {
        Injector.get().inject(this)

        category = Transformations.switchMap(categoryId) {
            categoryDao.findCategoryById(it)
        }
    }

    fun setCategoryId(categoryId: Long) {
        if (this.categoryId.value != categoryId) {
            this.categoryId.value = categoryId
        }
    }
}
