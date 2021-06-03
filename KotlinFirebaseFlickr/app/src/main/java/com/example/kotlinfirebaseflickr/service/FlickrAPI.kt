package com.example.kotlinfirebaseflickr.service

import com.example.kotlinfirebaseflickr.model.FlickrModel
import retrofit2.Call
import retrofit2.http.GET

interface FlickrAPI {

    @GET("services/rest/?method=flickr.photos.getRecent&api_key=fb8e8c6e70c912e010a8aa1a4ae0a4c6&format=json&nojsoncallback=1")
fun getData(): Call<List<FlickrModel>>



}