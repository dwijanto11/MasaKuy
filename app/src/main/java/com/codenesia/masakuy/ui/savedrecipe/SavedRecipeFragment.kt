package com.codenesia.masakuy.ui.savedrecipe

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.codenesia.masakuy.Utils
import com.codenesia.masakuy.Utils.checkTheme
import com.codenesia.masakuy.database.DetailRecipe
import com.codenesia.masakuy.database.Preferences
import com.codenesia.masakuy.database.Recipe
import com.codenesia.masakuy.database.SavedDetailRecipe
import com.codenesia.masakuy.databinding.FragmentSavedRecipeBinding
import com.codenesia.masakuy.ui.home.HomeFragmentDirections
import com.codenesia.masakuy.ui.home.RecipesAdapter

class SavedRecipeFragment : Fragment() {

    private lateinit var savedRecipeViewModel: SavedRecipeViewModel
    private var _binding: FragmentSavedRecipeBinding? = null
    private val binding get() = _binding!!
    private lateinit var savedRecipeAdapter: SavedRecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        savedRecipeViewModel =
            ViewModelProvider(this).get(SavedRecipeViewModel::class.java)

        _binding = FragmentSavedRecipeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var preferences = Preferences(requireContext())
        checkTheme(preferences.isThemeDark())

        savedRecipeViewModel.initDatabase(requireContext())
        settingRecyclerView()

        savedRecipeViewModel.allSavedRecipe().observe(viewLifecycleOwner, {
            if (it.size == 0)
                binding.status.visibility = View.VISIBLE
            savedRecipeAdapter.setData(it)
        })
    }

    fun settingRecyclerView() {
        savedRecipeAdapter = SavedRecipeAdapter(requireContext())
        savedRecipeAdapter.setOnItemClickCallback(object : SavedRecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(recipe: SavedDetailRecipe) {
                var key = ""
                var thumb = ""
                recipe.key?.let {
                    key = it
                }
                recipe.thumb?.let {
                    thumb = it
                }
                val toDetailFragment =
                    SavedRecipeFragmentDirections.actionNavigationSavedToNavigationDetail(
                        key,
                        thumb
                    )
                view?.findNavController()?.navigate(toDetailFragment)
            }
        })
        binding.apply {
            rvRecipes.layoutManager = LinearLayoutManager(requireContext())
            rvRecipes.adapter = savedRecipeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}