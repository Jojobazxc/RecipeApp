package com.example.recipeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.databinding.FragmentListCategoriesBinding

class CategoriesListFragment : Fragment() {

    private val binding: FragmentListCategoriesBinding by lazy {
        FragmentListCategoriesBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private fun initRecycler(){

        val dataSet = STUB.getCategories()
        val categoriesListAdapter = CategoriesListAdapter(dataSet)

        val recyclerView: RecyclerView = binding.rvCategories
        recyclerView.adapter = categoriesListAdapter

        recyclerView.adapter = categoriesListAdapter
        categoriesListAdapter.setOnItemClickListener(object : CategoriesListAdapter.OnItemClickListener {
            override fun onItemClick(categoryId: Int) {
                openRecipesByCategoryId(categoryId)
            }
        })

    }

    fun openRecipesByCategoryId(categoryId: Int) {
        val categoryName = STUB.getCategories()[categoryId].title
        val categoryImageUrl = STUB.getCategories()[categoryId].imageUrl

        val bundle = Bundle()
        bundle.putInt(ARG_CATEGORY_ID, categoryId)
        bundle.putString(ARG_CATEGORY_NAME, categoryName)
        bundle.putString(ARG_CATEGORY_IMAGE_URL, categoryImageUrl)

        parentFragmentManager.commit{
            replace<RecipesListFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

}

const val ARG_CATEGORY_ID = "category_id"
const val ARG_CATEGORY_NAME = "category_name"
const val ARG_CATEGORY_IMAGE_URL = "category_image_url"