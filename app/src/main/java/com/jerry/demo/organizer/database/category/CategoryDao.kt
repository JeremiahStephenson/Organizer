package com.jerry.demo.organizer.database.category

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

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
