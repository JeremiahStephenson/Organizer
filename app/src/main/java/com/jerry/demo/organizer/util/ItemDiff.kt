package com.jerry.demo.organizer.util

import androidx.recyclerview.widget.DiffUtil
import com.jerry.demo.organizer.database.PrimaryId

class ItemDiff<T : PrimaryId> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}