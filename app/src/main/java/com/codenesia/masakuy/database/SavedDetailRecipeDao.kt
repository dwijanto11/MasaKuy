package com.codenesia.masakuy.database

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedDetailRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(savedDetailRecipe: SavedDetailRecipe)

    @Query("SELECT * from saved_detail_recipe WHERE `key` = :key")
    fun get(key: String): Flow<SavedDetailRecipe>

    @Delete
    suspend fun delete(savedDetailRecipe: SavedDetailRecipe)

    @Query("SELECT * from saved_detail_recipe")
    fun getAll(): Flow<List<SavedDetailRecipe>>
}