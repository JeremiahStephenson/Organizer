package com.jerry.demo.organizer.database.item

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jerry.demo.organizer.database.Primary
import com.jerry.demo.organizer.database.category.Category

@Entity(tableName = "item",
        foreignKeys = arrayOf(ForeignKey(entity = Category::class, parentColumns = arrayOf("id"),
                childColumns = arrayOf("categoryId"), onDelete = ForeignKey.CASCADE)))
data class Item(@PrimaryKey(autoGenerate = true) override var id: Long = 0L,
                var categoryId: Long = 0L,
                var name: String = "",
                var description: String = "",
                var rating: Int = 0,
                var imagePath: String = "",
                var timestamp: Long = 0L) : Primary