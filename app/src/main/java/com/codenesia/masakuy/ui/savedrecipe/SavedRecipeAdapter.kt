package com.codenesia.masakuy.ui.savedrecipe

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
import com.codenesia.masakuy.database.DetailRecipe
import com.codenesia.masakuy.database.SavedDetailRecipe

class SavedRecipeAdapter(val context: Context) :
    RecyclerView.Adapter<SavedRecipeAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    private var listSavedRecipe = ArrayList<SavedDetailRecipe>()

    fun setData(values: List<SavedDetailRecipe>) {
        listSavedRecipe.clear()
        listSavedRecipe.addAll(values)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: SavedDetailRecipe)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipe, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val recipe = listSavedRecipe[position]
        holder.tvTitle.text = recipe.title
        holder.tvDescription.text = recipe.servings + " | "+recipe.times

        val imgUri = recipe.thumb?.toUri()?.buildUpon()?.scheme("https")?.build()
        holder.imageSampul.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listSavedRecipe[holder.adapterPosition])
        }
    }

    override fun getItemCount(): Int {
        return listSavedRecipe.size
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView = itemView.findViewById(R.id.title)
        var tvDescription: TextView = itemView.findViewById(R.id.description)
        var imageSampul: ImageView = itemView.findViewById(R.id.sampul)
    }
}