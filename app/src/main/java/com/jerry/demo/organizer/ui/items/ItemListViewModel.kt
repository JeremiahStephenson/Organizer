package com.jerry.demo.organizer.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import javax.inject.Inject

class ItemListViewModel
@Inject constructor(private val categoryDao: CategoryDao,
                    private val itemDao: ItemDao) : ViewModel() {

    private val categoryId = MutableLiveData<Long>()

    val category: LiveData<Category> = Transformations.switchMap(categoryId) {
        categoryDao.findCategoryById(it)
    }
    val items: LiveData<List<Item>> = Transformations.switchMap(categoryId) {
        itemDao.findItemsByCategoryId(it)
    }

    fun setCategoryId(categoryId: Long) {
        if (this.categoryId.value != categoryId) {
            this.categoryId.value = categoryId
        }
    }
}
