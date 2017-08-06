package com.jerry.demo.organizer.database.item

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: Item)

    @Update
    fun update(item: Item)

    @Query("DELETE FROM item")
    fun deleteAll()

    @Query("DELETE FROM item WHERE id = :id")
    fun deleteItem(id: Long)

    @Query("SELECT * FROM item")
    fun findAll(): List<Item>

    @Query("SELECT * FROM item WHERE id = :id")
    fun findItemById(id: Long): LiveData<Item>

    @Query("SELECT * FROM item WHERE categoryId = :categoryId ORDER BY timestamp DESC")
    fun findItemsByCategoryId(categoryId: Long): LiveData<List<Item>>

    @Query("UPDATE item SET rating = :rating WHERE id = :id")
    fun setItemRating(id: Long, rating: Int)
}
