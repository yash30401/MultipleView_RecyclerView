package com.devyash.multipleviewrecyclerview.network

import com.devyash.multipleviewrecyclerview.others.Constants.API
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object MovieApiService {

    val movieApi:MovieApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        movieApi = retrofit.create(MovieApi::class.java)
    }



}