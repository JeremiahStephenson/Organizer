package com.jerry.demo.organizer.ui.items

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import com.jerry.demo.organizer.inject.Injector
import javax.inject.Inject

class EditItemViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var itemDao: ItemDao

    private val itemId = MutableLiveData<Long>()

    var item: LiveData<Item>

    init {
        Injector.get().inject(this)

        item = Transformations.switchMap(itemId) {
            itemDao.findItemById(it)
        }
    }

    fun setItemId(itemId: Long) {
        if (this.itemId.value != itemId) {
            this.itemId.value = itemId
        }
    }
}
