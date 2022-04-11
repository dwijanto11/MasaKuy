package com.codenesia.masakuy.database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "recipe")
data class Recipe(
    @PrimaryKey
    @Json(name = "key")
    val key: String,

    @Json(name = "times")
    val times: String? = null,

    @Json(name = "thumb")
    val thumb: String? = null,

    @Json(name = "portion")
    val portion: String? = null,

    @Json(name = "title")
    val title: String? = null,

    @Json(name = "dificulty")
    val dificulty: String? = null
)