package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemCategoryBinding
import java.io.IOException
import java.io.InputStream


class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(categoryId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding: ItemCategoryBinding = ItemCategoryBinding.bind(itemView)

        var itemCard = binding.itemCard
        var cardImage: ImageView = binding.ivCardImage
        var cardTitle: TextView = binding.tvCardTitle
        var cardDescription: TextView = binding.tvCardDescription

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_category, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position]
        val categoryId = item.id
        viewHolder.itemCard.setOnClickListener { itemClickListener?.onItemClick(categoryId) }
        viewHolder.cardTitle.text = item.title
        viewHolder.cardDescription.text = item.description
        viewHolder.cardImage.contentDescription =
            viewHolder.itemView.context.getString(R.string.content_description_for_card_image) + " " + item.title
        try {
            val inputStream: InputStream? =
                viewHolder.itemView.context?.assets?.open(dataSet[position].imageUrl)
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            viewHolder.cardImage.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("RecipeApp", "${e.printStackTrace()}", e)
        }

    }

    override fun getItemCount() = dataSet.size

}
