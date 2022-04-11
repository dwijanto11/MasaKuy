package com.codenesia.masakuy.api

import com.codenesia.masakuy.database.Recipe
import com.squareup.moshi.Json

data class ResponseRecipes(

    @Json(name = "results")
    val results: List<Recipe>
)
