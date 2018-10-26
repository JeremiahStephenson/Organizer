package com.jerry.demo.organizer.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerry.demo.organizer.database.Primary

@Entity(tableName = "category")
class Category : Primary {
    @PrimaryKey(autoGenerate = true)
    override var id = 0L
    var name = ""
    var timestamp = 0L
}