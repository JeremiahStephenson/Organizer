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
    var timestamp = 0L

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Item) return false

        if (id != other.id) return false
        if (categoryId != other.categoryId) return false
        if (name != other.name) return false
        if (description != other.description) return false
        if (imagePath != other.imagePath) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + categoryId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + imagePath.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }


}