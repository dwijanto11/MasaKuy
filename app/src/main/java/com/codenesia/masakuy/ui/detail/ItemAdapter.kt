package com.codenesia.masakuy.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.codenesia.masakuy.R
import com.codenesia.masakuy.ui.home.RecipesAdapter

class ItemAdapter(val context: Context) :
    RecyclerView.Adapter<ItemAdapter.ListViewHolder>() {
    private var listItem = ArrayList<String>()

    fun setData(values: List<String>) {
        listItem.clear()
        listItem.addAll(values)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredient, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = listItem[position]
        holder.tvTitle.text = item
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
    }
}