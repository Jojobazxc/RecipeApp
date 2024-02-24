package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemCategoryBinding
import java.io.IOException
import java.io.InputStream


class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ItemCategoryBinding by lazy {
            ItemCategoryBinding.bind(itemView)
        }

        var cardImage: ImageView = binding.IvCardImage
        var cardTitle: TextView = binding.TvCardTitle
        var cardDescription: TextView = binding.TvCardDescription

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewHolder.cardTitle.text = item.title
        viewHolder.cardDescription.text = item.description
        viewHolder.cardImage.contentDescription = "Изображение категории ${item.title}"
        try {
            val inputStream: InputStream? = viewHolder.itemView.context?.assets?.open(dataSet[position].imageUrl)
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            viewHolder.cardImage.setImageDrawable(drawable)
        }catch (e: IOException){
            Log.e("RecipeApp", "${e.printStackTrace()}", e)
        }

    }

    override fun getItemCount() = dataSet.size

}
