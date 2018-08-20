package com.jerry.demo.organizer.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import kotlinx.android.synthetic.main.list_category.view.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    var data: List<Category> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (Category) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(parent).apply {
            // todo fix this
//            setOnClickListener { item ->
//                itemClickListener(data[item.adapterPosition])
//            }
        }
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.categoryTextView?.text = data[position].name
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    class CategoriesViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder (
            LayoutInflater.from(parent.context).inflate(R.layout.list_category, parent, false)) {
        val categoryTextView = itemView.categoryTextView
    }
}

