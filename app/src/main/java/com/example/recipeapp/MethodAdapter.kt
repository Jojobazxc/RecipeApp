package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.ItemMethodBinding

class MethodAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<MethodAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemMethodBinding = ItemMethodBinding.bind(itemView)

        val numberOfStep = binding.tvNumberOfStep
        val textOfStep = binding.tvTextOfStep

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_method, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(veiwholder: ViewHolder, position: Int) {
        val item = dataSet[position]
        veiwholder.numberOfStep.text = (position + 1).toString()
        veiwholder.textOfStep.text = item
    }

    override fun getItemCount() = dataSet.size

}