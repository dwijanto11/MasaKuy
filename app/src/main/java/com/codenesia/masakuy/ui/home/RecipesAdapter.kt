package com.codenesia.masakuy.ui.home

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
import com.codenesia.masakuy.database.Recipe

class RecipesAdapter(val context: Context) :
    RecyclerView.Adapter<RecipesAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    private var listRecipe = ArrayList<Recipe>()

    fun setData(values: List<Recipe>) {
        listRecipe.clear()
        listRecipe.addAll(values)
        notifyDataSetChanged()
    }

    fun clearData() {
        listRecipe.clear()
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Recipe)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val recipe = listRecipe[position]
        holder.tvTitle.text = recipe.title
        holder.tvDescription.text = recipe.portion + " | "+recipe.times

        val imgUri = recipe.thumb?.toUri()?.buildUpon()?.scheme("https")?.build()
        holder.imageSampul.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listRecipe[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listRecipe.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        var tvDescription: TextView = itemView.findViewById(R.id.description)
        var imageSampul: ImageView = itemView.findViewById(R.id.sampul)
    }
}