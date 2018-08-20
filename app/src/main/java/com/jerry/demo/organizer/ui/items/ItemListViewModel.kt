package com.jerry.demo.organizer.ui.items

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import com.jerry.demo.organizer.inject.Injector
import javax.inject.Inject

class ItemListViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var categoryDao: CategoryDao
    @Inject
    lateinit var itemDao: ItemDao

    private val categoryId = MutableLiveData<Long>()

    var category: LiveData<Category>
    var items: LiveData<List<Item>>

    init {
        Injector.get().inject(this)

        category = Transformations.switchMap(categoryId) {
            categoryDao.findCategoryById(it)
        }

        items = Transformations.switchMap(categoryId) {
            itemDao.findItemsByCategoryId(it)
        }
    }

    fun setCategoryId(categoryId: Long) {
        if (this.categoryId.value != categoryId) {
            this.categoryId.value = categoryId
        }
    }
}
