package com.codenesia.masakuy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Recipe::class, DetailRecipe::class,SavedDetailRecipe::class], version = 1)
@TypeConverters(Converters::class)
abstract class DatabaseApp : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
    abstract fun detailRecipeDao(): DetailRecipeDao
    abstract fun savedDetailRecipeDao(): SavedDetailRecipeDao
    companion object {
        @Volatile
        private var INSTANCE: DatabaseApp? = null
        @JvmStatic
        fun getDatabase(context: Context): DatabaseApp {
            if (INSTANCE == null) {
                synchronized(DatabaseApp::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DatabaseApp::class.java, "db_masakuy")
                        .build()
                }
            }
            return INSTANCE as DatabaseApp
        }
    }
}