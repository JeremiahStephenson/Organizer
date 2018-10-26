package com.jerry.demo.organizer.database.item

import androidx.recyclerview.widget.DiffUtil
import com.jerry.demo.organizer.database.Primary

class ItemDiff<T : Primary> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}