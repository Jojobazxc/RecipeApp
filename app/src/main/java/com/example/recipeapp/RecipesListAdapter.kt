package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemRecipesBinding
import java.io.IOException
import java.io.InputStream

class RecipesListAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemRecipesBinding = ItemRecipesBinding.bind(itemView)

        var itemCard = binding.recipeItemCard
        var title: TextView = binding.tvCardRecipesTitle
        var imageUrl: ImageView = binding.ivCardRecipesImage
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_recipes, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        val recipeId = item.id
        viewHolder.itemCard.setOnClickListener { itemClickListener?.onItemClick(recipeId) }
        viewHolder.title.text = item.title
        try {
            val inputStream: InputStream? =
                viewHolder.itemView.context?.assets?.open(dataSet[position].imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageUrl.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("CategoriesListAdapter", "${e.printStackTrace()}", e)
        }
    }

    override fun getItemCount() = dataSet.size

}