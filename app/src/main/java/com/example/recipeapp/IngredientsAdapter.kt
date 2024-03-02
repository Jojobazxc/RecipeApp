package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemIngredientsBinding


class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemIngredientsBinding = ItemIngredientsBinding.bind(itemView)

        val nameOfIngredient = binding.tvNameOfIngredient
        val quantityOfIngredient = binding.tvQuantityOfIngredient
        val unitOfMeasure = binding.tvUnitOfMeasure
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_ingredients, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewholder: ViewHolder, position: Int) {
        val item = dataSet[position]
        viewholder.nameOfIngredient.text = item.description
        viewholder.quantityOfIngredient.text = item.quantity
        viewholder.unitOfMeasure.text = item.unitOfMeasure
    }

    override fun getItemCount() = dataSet.size
}