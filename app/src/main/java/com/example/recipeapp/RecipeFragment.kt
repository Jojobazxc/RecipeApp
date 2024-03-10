package com.example.recipeapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
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

    private fun initArguments() {
        recipe = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
    }

    private fun initUi() {
        binding.tvHeaderTitle.text = recipe?.title
        binding.ibIcHeart.setBackgroundResource(R.drawable.ic_heart_empty)
        binding.ibIcHeart.setOnClickListener{
            binding.ibIcHeart.setBackgroundResource(R.drawable.ic_heart)
        }
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

        val dividerItemDecoration = DividerItemDecoration(context, RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.shape_divider_recycler_view, null)?.let {
            dividerItemDecoration.setDrawable(it)
            with(binding) {
                rvIngredients.addItemDecoration(dividerItemDecoration)
                rvMethod.addItemDecoration(dividerItemDecoration)
            }
        }
        val sizeLnDp = resources.getDimensionPixelSize(R.dimen.quarter_main_dimen)
        binding.sbPortions.setPadding(sizeLnDp, 0, sizeLnDp, 0)
        binding.tvNumberOfPortions.text = 1.toString()
        binding.sbPortions.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                recyclerViewOfIngredientsAdapter?.updateIngredients(progress)
                binding.tvNumberOfPortions.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}

