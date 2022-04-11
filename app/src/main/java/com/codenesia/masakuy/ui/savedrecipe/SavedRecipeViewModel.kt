package com.codenesia.masakuy.ui.savedrecipe

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.codenesia.masakuy.database.DatabaseApp
import com.codenesia.masakuy.database.DetailRecipe
import com.codenesia.masakuy.database.SavedDetailRecipe
import com.codenesia.masakuy.database.SavedDetailRecipeDao

class SavedRecipeViewModel : ViewModel() {
    private lateinit var mSavedDetailRecipeDao: SavedDetailRecipeDao

    fun allSavedRecipe(): LiveData<List<SavedDetailRecipe>> = mSavedDetailRecipeDao.getAll().asLiveData()

    fun initDatabase(context: Context) {
        val db = DatabaseApp.getDatabase(context)
        mSavedDetailRecipeDao = db.savedDetailRecipeDao()
    }


}