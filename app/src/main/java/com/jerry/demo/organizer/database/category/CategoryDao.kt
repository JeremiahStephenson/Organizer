package com.jerry.demo.organizer.database.category

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category)

    @Query("DELETE FROM category")
    fun deleteAll()

    @Query("DELETE FROM category WHERE id = :id")
    fun deleteCategory(id: Long)

    @Query("SELECT * FROM category ORDER BY timestamp DESC")
    fun findAll(): List<Category>

    @Query("SELECT * FROM category ORDER BY timestamp DESC")
    fun findAllLive(): LiveData<List<Category>>

    @Query("SELECT * FROM category WHERE id = :id")
    fun findCategoryById(id: Long): LiveData<Category>
}
