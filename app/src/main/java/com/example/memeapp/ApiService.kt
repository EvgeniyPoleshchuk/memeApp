package com.example.memeapp

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET

interface MemeDataApi{

    @GET("/get_memes")
    suspend fun getData():MemeData

}
object RetroFit {
    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.imgflip.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var memeDataApi: MemeDataApi = retrofit.create(MemeDataApi::class.java)
}