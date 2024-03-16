package com.example.recipeapp


import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentListRecipesBinding
import java.io.IOException
import java.io.InputStream

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
        initArguments()
        initUI()
        initRecycler()
    }

    fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(
            ARG_RECIPE to recipe
        )
        parentFragmentManager.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun initUI() {
        binding.tvHeaderTitle.text = categoryName
        try {
            val inputStream: InputStream? =
                categoryImageUrl?.let { binding.ivHeaderImage.context?.assets?.open(it) }
            val drawable: Drawable? = Drawable.createFromStream(inputStream, null)
            binding.ivHeaderImage.setImageDrawable(drawable)
        } catch (e: IOException) {
            Log.e("RecipeApp", "${e.printStackTrace()}", e)
        }
    }

    private fun initArguments() {
        arguments.let { args ->
            categoryId = args?.getInt(ARG_CATEGORY_ID)
            categoryName = args?.getString(ARG_CATEGORY_NAME)
            categoryImageUrl = args?.getString(ARG_CATEGORY_IMAGE_URL)
        }
    }

    private fun initRecycler() {
        val dataset = categoryId?.let { STUB.getRecipesByCategoryId(it) }
        val recipesListAdapter: RecipesListAdapter? = dataset?.let { RecipesListAdapter(it) }

        val recyclerView: RecyclerView = binding.rvRecipes
        recyclerView.adapter = recipesListAdapter
        recipesListAdapter?.setOnItemClickListener(object :
            RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

}