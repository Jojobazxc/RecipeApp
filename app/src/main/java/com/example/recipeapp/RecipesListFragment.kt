package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.FragmentListRecipesBinding

class RecipesListFragment : Fragment(R.layout.fragment_list_recipes) {
    private val binding: FragmentListRecipesBinding by lazy {
        FragmentListRecipesBinding.inflate(layoutInflater)
    }

    private var categoryId: Int? = null
    private var categoryName: String? = null
    private var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = requireArguments().getInt("category_id")
        categoryName = requireArguments().getString("category_name")
        categoryImageUrl = requireArguments().getString("category_image_url")
    }

}