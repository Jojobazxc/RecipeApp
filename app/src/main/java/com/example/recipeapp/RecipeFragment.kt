package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recipeapp.databinding.FragmentRecipeBinding
import java.io.IOException
import java.io.InputStream

class RecipeFragment : Fragment(R.layout.fragment_recipe) {
    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }
    private var recipe: Recipe? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initArguments()
        initUi()
        initRecycler()

    }

    private fun initArguments(): Recipe? {
        recipe = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
        return recipe
    }
    private fun initUi() {
        binding.tvHeaderTitle.text = recipe?.title
        try {
            val categoryImageUrl = recipe?.imageUrl
            val inputStream: InputStream? =
                categoryImageUrl?.let { binding.ivHeaderImage.context?.assets?.open(it) }
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            binding.ivHeaderImage.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("RecipeApp", "${e.printStackTrace()}", e)
        }
    }

    private fun initRecycler() {
        val dataSetForIngredients: List<Ingredient>? = recipe?.ingredients
        val dataSetForMethod: List<String>? = recipe?.method

        val recyclerViewOfIngredientsAdapter = dataSetForIngredients?.let { IngredientsAdapter(it) }
        val recyclerViewOfMethodAdapter = dataSetForMethod?.let { MethodAdapter(it) }

        val recyclerViewOfIngredients = binding.rvIngredients
        val recyclerViewOfMethod = binding.rvMethod

        recyclerViewOfIngredients.adapter = recyclerViewOfIngredientsAdapter
        recyclerViewOfMethod.adapter = recyclerViewOfMethodAdapter

        val dividerHeight = resources.getDimensionPixelSize(R.dimen.divider_height)
        val itemDecoration = context?.let { CustomItemDecoration(it, dividerHeight) }
        itemDecoration?.let { binding.rvIngredients.addItemDecoration(it) }
        itemDecoration?.let { binding.rvMethod.addItemDecoration(it) }

    }
}

