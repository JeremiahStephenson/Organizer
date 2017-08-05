package com.jerry.demo.organizer.database.item

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.jerry.demo.organizer.database.category.Category

@Entity(tableName = "item",
        foreignKeys = arrayOf(ForeignKey(entity = Category::class, parentColumns = arrayOf("id"),
                childColumns = arrayOf("categoryId"), onDelete = ForeignKey.CASCADE)))
class Item {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var categoryId = 0L
    var name = ""
    var description = ""
    var rating = 0
    var imagePath = ""
}