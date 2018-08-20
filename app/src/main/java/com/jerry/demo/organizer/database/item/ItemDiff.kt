package com.jerry.demo.organizer.database.item

import androidx.recyclerview.widget.DiffUtil

// todo change to async diff
class ItemDiff(val newItems: List<Item>, val oldItems: List<Item>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems.get(oldItemPosition).id == newItems.get(newItemPosition).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems.get(oldItemPosition) == newItems.get(newItemPosition)
    }
}