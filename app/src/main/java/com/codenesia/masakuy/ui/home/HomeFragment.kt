package com.codenesia.masakuy.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.codenesia.masakuy.R
import com.codenesia.masakuy.Utils
import com.codenesia.masakuy.Utils.checkTheme
import com.codenesia.masakuy.database.Preferences
import com.codenesia.masakuy.database.Recipe
import com.codenesia.masakuy.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recipesAdapter: RecipesAdapter
    private var recipes: List<Recipe>? = null
    private var STATUS = ApiStatus.LOADING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var preferences = Preferences(requireContext())
        checkTheme(preferences.isThemeDark())

        homeViewModel.initDatabase(requireContext())
        settingRecyclerView()
        homeViewModel.getRecipes()

        setSearchView()

        setObserve()
    }

    fun setSearchView() {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                recipesAdapter.clearData()
                query?.let { homeViewModel.searchRecipes(it) }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

    }

    fun setObserve() {
        homeViewModel.recipes.observe(viewLifecycleOwner, {
            recipes = it.results
            homeViewModel.insertRecipes(it.results)
            recipesAdapter.setData(it.results)
        })

        homeViewModel.getFromDB().observe(viewLifecycleOwner, {
            recipes = it
            if (STATUS == ApiStatus.ERROR)
                recipesAdapter.setData(it)
        })

        homeViewModel.status.observe(viewLifecycleOwner, {
            STATUS = it
            when (it) {
                ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ApiStatus.DONE -> binding.progressBar.visibility = View.GONE
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    view?.let { it1 ->
                        Snackbar.make(
                            it1,
                            "Tidak Dapat Terhubung ke Internet",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    recipes?.let {
                        recipesAdapter.setData(it)
                    }
                }
            }
        })
    }

    fun settingRecyclerView() {
        recipesAdapter = RecipesAdapter(requireContext())
        recipesAdapter.setOnItemClickCallback(object : RecipesAdapter.OnItemClickCallback {
            override fun onItemClicked(recipe: Recipe) {
                var key = ""
                var thumb = ""
                recipe.key?.let {
                    key = it
                }
                recipe.thumb?.let {
                    thumb = it
                }
                val toDetailFragment =
                    HomeFragmentDirections.actionNavigationHomeToNavigationDetail(key, thumb)
                view?.findNavController()?.navigate(toDetailFragment)
            }
        })
        binding.apply {
            rvRecipes.layoutManager = LinearLayoutManager(requireContext())
            rvRecipes.adapter = recipesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}