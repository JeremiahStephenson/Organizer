package com.jerry.demo.organizer.ui.category


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder
import com.jerry.demo.organizer.R
import com.jerry.demo.organizer.database.category.Category
import kotlinx.android.synthetic.main.list_item_category.view.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    var data: List<Category> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var itemClickListener: (Category) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(parent).apply {
            setOnClickListener { item ->
                itemClickListener(data[item.adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder?, position: Int) {
        holder?.categoryTextView?.text = data[position].name
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    class CategoriesViewHolder(parent: ViewGroup) : ClickableViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_category, parent, false)) {
        val categoryTextView = itemView.categoryTextView
    }
}

