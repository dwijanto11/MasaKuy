package com.codenesia.masakuy.api

import com.codenesia.masakuy.database.DetailRecipe
import com.squareup.moshi.Json

data class ResponseDetailRecipe(

	@Json(name="results")
	val results: DetailRecipe
)