package com.jerry.demo.organizer.ui.items


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.item.Item
import kotlinx.android.synthetic.main.list_item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    var data: List<Item> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (Item) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(parent).apply {
            setOnClickListener { item ->
                itemClickListener(data[item.adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: ItemsViewHolder?, position: Int) {
        holder?.let {
            val item = data[position]
            it.itemTextView.text = item.name
            it.descriptionTextView.text = item.description
            it.photoImageView.visibility = if (item.imagePath.isNullOrEmpty()) View.GONE else View.VISIBLE
            Glide.with(it.photoImageView.context).load(data[position].imagePath).into(it.photoImageView)
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    class ItemsViewHolder(parent: ViewGroup) : ClickableViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)) {
        val itemTextView = itemView.itemTextView
        val photoImageView = itemView.photoImageView
        val descriptionTextView = itemView.descriptionTextView
    }
}

