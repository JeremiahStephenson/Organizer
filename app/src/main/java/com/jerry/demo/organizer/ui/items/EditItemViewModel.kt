package com.jerry.demo.organizer.ui.items

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import javax.inject.Inject

class EditItemViewModel
@Inject constructor(private val itemDao: ItemDao) : ViewModel() {

    private val itemId = MutableLiveData<Long>()

    var imagePath: String? = null

    val item: LiveData<Item> = Transformations.switchMap(itemId) {
        itemDao.findItemById(it)
    }

    fun setItemId(itemId: Long) {
        if (this.itemId.value != itemId) {
            this.itemId.value = itemId
        }
    }
}
