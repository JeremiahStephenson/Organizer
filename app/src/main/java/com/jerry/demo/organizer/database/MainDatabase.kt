package com.jerry.demo.organizer.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDao

@Database(
        entities = arrayOf(
                Category::class,
                Item::class
        ),
        version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun itemDao(): ItemDao

    companion object {
        const val DATABASE_NAME = "main"
    }
}