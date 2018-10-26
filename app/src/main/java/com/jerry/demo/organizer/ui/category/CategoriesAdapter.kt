package com.jerry.demo.organizer.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import com.jerry.demo.organizer.util.ItemDiff
import kotlinx.android.synthetic.main.list_category.view.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    private val differ = AsyncListDiffer(this, ItemDiff<Category>())

    var data: List<Category> = mutableListOf()
        set(value) {
            field = value
            differ.submitList(value)
        }

    var itemClickListener: (Category) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder = CategoriesViewHolder(parent)

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.categoryTextView?.text = differ.currentList[position].name
    }

    override fun getItemCount(): Int = differ.currentList.count()

    inner class CategoriesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.list_category, parent, false)) {
        val categoryTextView = itemView.categoryTextView
        init {
            itemView.categoryContainer.setOnClickListener {
                itemClickListener(differ.currentList[adapterPosition])
            }
        }
    }
}

