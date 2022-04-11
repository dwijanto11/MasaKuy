package com.codenesia.masakuy.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL =
    "https://masak-apa-tomorisakura.vercel.app/api/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ApiService {

    @GET("recipes/{page}")
    suspend fun getRecipes(@Path("page") page: String): ResponseRecipes

    @GET("search/")
    suspend fun searchRecipes(@Query("q") query: String): ResponseRecipes

    @GET("recipe/{key}")
    suspend fun getDetailRecipe(@Path("key") key: String): ResponseDetailRecipe
}

object Api {
    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}