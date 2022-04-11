package com.codenesia.masakuy.database

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.Json
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "saved_detail_recipe")
data class SavedDetailRecipe(
    @PrimaryKey
    var key: String = "",

    @Json(name="servings")
    val servings: String? = null,

    @Json(name="times")
    val times: String? = null,

    @Json(name="ingredient")
    val ingredient: List<String>? = null,

    @Json(name="thumb")
    var thumb: String? = null,

    @Json(name="step")
    val step: List<String>? = null,

    @Json(name="title")
    val title: String? = null,

    @Json(name="dificulty")
    val dificulty: String? = null,

    @Json(name="desc")
    val desc: String? = null
)