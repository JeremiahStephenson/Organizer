package com.jerry.demo.organizer

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.jerry.demo.organizer.database.MainDatabase
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var db: MainDatabase
    lateinit var itemDao: ItemDao
    lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        db = Room.inMemoryDatabaseBuilder(context, MainDatabase::class.java).build()
        itemDao = db.itemDao()
        categoryDao = db.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testItemInsert() {
        categoryDao.insert(Category().apply {
            name = "Category"
        })

        val categories = categoryDao.findAll()

        check(categories.size == 1)

        itemDao.insert(Item().apply {
            name = "Test"
            description = "Description"
            timestamp = Calendar.getInstance().timeInMillis
            categoryId = categories.first().id
        })

        val list = itemDao.findAll()

        check(list.size == 1)
    }
}
