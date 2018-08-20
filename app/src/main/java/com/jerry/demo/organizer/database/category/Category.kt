package com.jerry.demo.organizer.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
class Category {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var name = ""
    var timestamp = 0L
}