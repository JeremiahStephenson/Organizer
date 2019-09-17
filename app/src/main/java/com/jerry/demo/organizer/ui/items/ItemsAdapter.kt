package com.jerry.demo.organizer.ui.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.item.Item
import com.jerry.demo.organizer.util.ItemDiff
import kotlinx.android.synthetic.main.list_item.view.*
import java.io.File

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    private val differ = AsyncListDiffer(this, ItemDiff<Item>())

    var data: List<Item> = mutableListOf()
        set(value) {
            field = value
            differ.submitList(value)
        }

    var itemClickListener: (Item) -> Unit = {}
    var onRatingClickListener: (Item, Int) -> Unit = { _, _ ->}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsViewHolder = ItemsViewHolder(parent)

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        val item = differ.currentList[position]
        if (item != holder.item) {
            holder.itemTextView.text = item.name
            holder.descriptionTextView.text = item.description
            holder.ratingBar.rating = item.rating.toFloat()
            holder.photoImageView.load(File(item.imagePath))
            //Glide.with(holder.photoImageView.context).load(item.imagePath).into(holder.photoImageView)
        }
    }

    override fun getItemCount(): Int = differ.currentList.count()

    inner class ItemsViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)) {
        var item: Item? = null
        val itemTextView = itemView.itemTextView
        val photoImageView = itemView.photoImageView
        val descriptionTextView = itemView.descriptionTextView
        val ratingBar = itemView.ratingBar

        init {
            ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                onRatingClickListener(differ.currentList[adapterPosition], rating.toInt())
            }
            itemView.itemContainer.setOnClickListener { itemClickListener(differ.currentList[adapterPosition]) }
        }
    }
}

