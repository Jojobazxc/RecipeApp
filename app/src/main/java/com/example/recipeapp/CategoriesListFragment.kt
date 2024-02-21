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
            override fun onItemClick() {
                openRecipesByCategoryId()
            }
        })

    }

    fun openRecipesByCategoryId() {
        parentFragmentManager.commit{
            replace<RecipesListFragment>(R.id.mainContainer)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

}