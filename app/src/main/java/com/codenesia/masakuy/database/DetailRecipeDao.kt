package com.codenesia.masakuy.database

import android.content.ClipData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DetailRecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(detailRecipe: DetailRecipe)

    @Query("SELECT * from detail_recipe WHERE `key` = :key")
    fun get(key: String): Flow<DetailRecipe>

    @Delete
    suspend fun delete(detailRecipe: DetailRecipe)

    @Query("SELECT * from detail_recipe")
    fun getAll(): Flow<List<DetailRecipe>>
}