package com.example.kotlinfirebaseflickr.model

import com.google.gson.annotations.SerializedName

data class FlickrModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("owner")
    val owner: String,
    @SerializedName("secret")
    val secret: String,
    @SerializedName("title")
    val title: String) {
}