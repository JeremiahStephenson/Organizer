package com.jerry.demo.organizer.ui.items

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import com.jerry.demo.organizer.inject.Injector
import javax.inject.Inject

class EditItemViewModel
@Inject constructor(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var itemDao: ItemDao

    private val itemId = MutableLiveData<Long>()

    var imagePath: String? = null
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
