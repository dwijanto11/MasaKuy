package com.codenesia.masakuy.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.codenesia.masakuy.R
import com.codenesia.masakuy.Utils
import com.codenesia.masakuy.Utils.checkTheme
import com.codenesia.masakuy.database.DetailRecipe
import com.codenesia.masakuy.database.Preferences
import com.codenesia.masakuy.database.SavedDetailRecipe
import com.codenesia.masakuy.databinding.FragmentDetailBinding
import com.codenesia.masakuy.ui.home.ApiStatus
import com.google.android.material.snackbar.Snackbar

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null

    private val binding get() = _binding!!
    private lateinit var ingredientAdapter: ItemAdapter
    private lateinit var stepAdapter: ItemAdapter
    private lateinit var key: String
    private lateinit var urlThumbnail: String
    private var detailRecipe : DetailRecipe? = null
    private lateinit var savedDetailRecipe: SavedDetailRecipe
    private var SAVED = false
    private var STATUS = ApiStatus.LOADING

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailViewModel =
            ViewModelProvider(this).get(DetailViewModel::class.java)

        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var preferences = Preferences(requireContext())
        checkTheme(preferences.isThemeDark())

        binding.mainLayout.visibility = View.GONE
        settingRecyclerView()
        detailViewModel.initDatabase(requireContext())

        key = DetailFragmentArgs.fromBundle(arguments as Bundle).key
        urlThumbnail = DetailFragmentArgs.fromBundle(arguments as Bundle).thumb
        detailViewModel.getDetailRecipe(key)

        binding.btnSaved.setOnClickListener({
            when (SAVED) {
                true -> {
                    savedDetailRecipe.key = key
                    if (savedDetailRecipe.thumb == null)
                        savedDetailRecipe.thumb = urlThumbnail
                    detailViewModel.deleteSavedRecipe(savedDetailRecipe)
                }
                else -> {
                    savedDetailRecipe.key = key
                    if (savedDetailRecipe.thumb != null)
                        savedDetailRecipe.thumb = urlThumbnail
                    detailViewModel.insertSavedRecipe(savedDetailRecipe)
                }
            }
            detailViewModel.setSavedRecipeStatus(!SAVED)
        })

        Log.i("datass","zzzzzz "+detailRecipe.toString())
        getObserve()
    }

    fun getObserve() {
        detailViewModel.recipe.observe(viewLifecycleOwner, {
            savedDetailRecipe = detailViewModel.toSavedDetailRecipe(it.results)
            setDataToUI(it.results)
            Log.i("datass","dddd")
            detailRecipe = it.results

            detailRecipe?.key = key
            detailRecipe?.thumb = urlThumbnail
            detailViewModel.insertRecipe(detailRecipe!!)
        })
        detailViewModel.getSavedRecipeFromDB(key).observe(this.viewLifecycleOwner) { data ->
            if (data != null)
                savedDetailRecipe = data
            when (data) {
                null -> detailViewModel.setSavedRecipeStatus(false)
                else -> detailViewModel.setSavedRecipeStatus(true)
            }
        }
        detailViewModel.savedRecipe.observe(viewLifecycleOwner, { savedRecipe ->
            SAVED = savedRecipe
            when (savedRecipe) {
                true -> binding.btnSaved.setBackgroundResource(R.drawable.ic_saved)
                else -> binding.btnSaved.setBackgroundResource(R.drawable.ic_saved_border)
            }
        })
        detailViewModel.status.observe(viewLifecycleOwner, {
            STATUS = it
            when (it) {
                ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ApiStatus.DONE -> binding.progressBar.visibility = View.GONE
                ApiStatus.ERROR -> {
                    if (detailRecipe != null) {
                        Log.i("datass","aaaaa "+detailRecipe.toString())
                        setDataToUI(detailRecipe!!)
                    }
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(view!!, "Tidak Dapat Terhubung ke Internet", Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        })
        detailViewModel.getRecipeFromDB(key).observe(this.viewLifecycleOwner) { data ->
            if (data != null)
                detailRecipe = data
            if (STATUS == ApiStatus.ERROR) {
                if (data != null) {
                    Log.i("datass","bbbb")
                    setDataToUI(data)
                }
            }
        }
    }


    fun settingRecyclerView() {
        ingredientAdapter = ItemAdapter(requireContext())
        binding.apply {
            rvIngredient.layoutManager = LinearLayoutManager(requireContext())
            rvIngredient.adapter = ingredientAdapter
        }

        stepAdapter = ItemAdapter(requireContext())
        binding.apply {
            rvStep.layoutManager = LinearLayoutManager(requireContext())
            rvStep.adapter = stepAdapter
        }
    }

    fun setDataToUI(data: DetailRecipe) {
        binding.mainLayout.visibility = View.VISIBLE
        data.thumb?.let {
            urlThumbnail = it
        }
        val imgUri = urlThumbnail.toUri().buildUpon().scheme("https").build()

        binding.apply {
            progressBar.visibility = View.GONE
            title.text = data.title
            sampul.load(imgUri) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            times.text = data.servings + " | " + data.times

            data.ingredient?.let {
                ingredientAdapter.setData(it)
            }
            data.step?.let {
                stepAdapter.setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}