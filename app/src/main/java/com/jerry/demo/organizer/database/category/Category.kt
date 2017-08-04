package com.jerry.demo.organizer.database.category

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "category")
class Category {
    @PrimaryKey(autoGenerate = true)
    var id = 0L
    var name = ""
}