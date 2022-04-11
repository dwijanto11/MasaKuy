package com.codenesia.masakuy.ui.home

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.codenesia.masakuy.api.Api
import com.codenesia.masakuy.api.ResponseRecipes
import com.codenesia.masakuy.database.*
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class HomeViewModel : ViewModel() {
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status

    private var _recipes = MutableLiveData<ResponseRecipes>()
    val recipes: LiveData<ResponseRecipes> get() = _recipes

    private lateinit var recipeDao: RecipeDao

    fun getRecipes() {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                val result = Api.retrofitService.getRecipes("1")
                _status.value = ApiStatus.DONE
                _recipes.value = result
            } catch (e: Exception) {
                Log.i("datass", e.toString())
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun searchRecipes(query:String) {
        _status.value = ApiStatus.LOADING
        viewModelScope.launch {
            try {
                val result = Api.retrofitService.searchRecipes(query)
                _status.value = ApiStatus.DONE
                _recipes.value = result
            } catch (e: Exception) {
                Log.i("datass", e.toString())
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun initDatabase(context: Context) {
        val db = DatabaseApp.getDatabase(context)
        recipeDao = db.recipeDao()
    }

    fun insertRecipes(recipes: List<Recipe>) {
        viewModelScope.launch {
            recipeDao.insertAll(*recipes.toTypedArray())
        }
    }

    fun getFromDB(): LiveData<List<Recipe>> {
        return recipeDao.getAll().asLiveData()
    }
}