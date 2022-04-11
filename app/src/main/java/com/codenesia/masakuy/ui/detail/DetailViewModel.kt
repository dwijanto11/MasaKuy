package com.codenesia.masakuy.ui.detail

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.codenesia.masakuy.api.Api
import com.codenesia.masakuy.api.ResponseDetailRecipe
import com.codenesia.masakuy.database.*
import com.codenesia.masakuy.ui.home.ApiStatus
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var _recipe = MutableLiveData<ResponseDetailRecipe>()
    val recipe: LiveData<ResponseDetailRecipe> get() = _recipe

    private val _savedRecipe = MutableLiveData<Boolean>()
    val savedRecipe: LiveData<Boolean> = _savedRecipe

    private lateinit var mSavedDetailRecipeDao: SavedDetailRecipeDao
    private lateinit var mDetailRecipeDao: DetailRecipeDao

    fun getDetailRecipe(key: String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                val result = Api.retrofitService.getDetailRecipe(key)
                _status.value = ApiStatus.DONE
                _recipe.value = result
            } catch (e: Exception) {
                Log.i("datass", e.toString())
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun initDatabase(context: Context) {
        val db = DatabaseApp.getDatabase(context)
        mSavedDetailRecipeDao = db.savedDetailRecipeDao()
        mDetailRecipeDao = db.detailRecipeDao()
    }

    fun insertSavedRecipe(savedDetailRecipe: SavedDetailRecipe) {
        viewModelScope.launch {
            mSavedDetailRecipeDao.insert(savedDetailRecipe)
        }
    }

    fun deleteSavedRecipe(savedDetailRecipe: SavedDetailRecipe) {
        viewModelScope.launch {
            mSavedDetailRecipeDao.delete(savedDetailRecipe)
            Log.i("delets", "let")
        }
    }

    fun getSavedRecipeFromDB(key: String): LiveData<SavedDetailRecipe> {
        return mSavedDetailRecipeDao.get(key).asLiveData()
    }

    fun setSavedRecipeStatus(value: Boolean) {
        _savedRecipe.postValue(value)
    }

    fun toSavedDetailRecipe(detailRecipe: DetailRecipe)
            : SavedDetailRecipe {
        val savedDetailRecipe = SavedDetailRecipe(detailRecipe.key
            ,detailRecipe.servings,detailRecipe.times,detailRecipe.ingredient
            ,detailRecipe.thumb,detailRecipe.step,detailRecipe.title
            ,detailRecipe.dificulty,detailRecipe.desc)
        return savedDetailRecipe
    }

    fun insertRecipe(detailRecipe: DetailRecipe) {
        viewModelScope.launch {
            mDetailRecipeDao.insert(detailRecipe)
        }
    }

    fun getRecipeFromDB(key: String): LiveData<DetailRecipe> {
        return mDetailRecipeDao.get(key).asLiveData()
    }
}