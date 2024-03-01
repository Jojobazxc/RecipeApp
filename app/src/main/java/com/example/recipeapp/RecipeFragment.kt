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

class RecipeFragment: Fragment(R.layout.fragment_recipe) {
    private val binding: FragmentRecipeBinding by lazy {
        FragmentRecipeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipe: Recipe? = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
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
}

