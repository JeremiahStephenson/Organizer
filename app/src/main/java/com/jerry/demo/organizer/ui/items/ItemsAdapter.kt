package com.jerry.demo.organizer.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.database.item.ItemDiff
import kotlinx.android.synthetic.main.list_item.view.*

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    var data: List<Item> = mutableListOf()
        set(value) {
            val diff = DiffUtil.calculateDiff(ItemDiff(value, data))
            field = value
            diff.dispatchUpdatesTo(this)
        }

    var itemClickListener: (Item) -> Unit = {}
    var onRatingClickListener: (Item, Int) -> Unit = { _, _ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder {
        return ItemsViewHolder(parent).apply {
            // todo fix this
//            setOnClickListener { item ->
//                itemClickListener(data[item.adapterPosition])
//            }
            onRatingClick = { item, rating ->
                onRatingClickListener(data[item.adapterPosition], rating)
            }
        }
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = data[position]
        if (item != holder.item) {
            holder.itemTextView.text = item.name
            holder.descriptionTextView.text = item.description
            holder.ratingBar.rating = item.rating.toFloat()
            Glide.with(holder.photoImageView.context).load(data[position].imagePath).into(holder.photoImageView)
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    class ItemsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)) {
        var item: Item? = null
        val itemTextView = itemView.itemTextView
        val photoImageView = itemView.photoImageView
        val descriptionTextView = itemView.descriptionTextView
        val ratingBar = itemView.ratingBar

        init {
            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                onRatingClick(this, rating.toInt())
            }
        }

        var onRatingClick: (RecyclerView.ViewHolder, Int) -> Unit = { _, _ -> }
    }
}

