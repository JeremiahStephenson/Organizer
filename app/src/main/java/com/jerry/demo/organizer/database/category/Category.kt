package com.jerry.demo.organizer.database.category

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jerry.demo.organizer.database.PrimaryId

@Entity(tableName = "category")
class Category : PrimaryId {
    @PrimaryKey(autoGenerate = true)
    override var id = 0L
    var name = ""
    var timestamp = 0L
}