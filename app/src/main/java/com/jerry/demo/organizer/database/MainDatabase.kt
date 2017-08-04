package com.jerry.demo.organizer.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.database.category.CategoryDao

@Database(
        entities = arrayOf(
                Category::class
        ),
        version = 1)
abstract class MainDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    companion object {
        const val DATABASE_NAME = "main"
    }
}