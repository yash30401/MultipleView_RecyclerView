package com.devyash.multipleviewrecyclerview.network

import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import retrofit2.Response
import retrofit2.http.GET

interface MovieApi {

    @GET("/movies")
    suspend fun getMovies():Response<List<HomeRecylerViewItem.Movie>>

    @GET("/directors")
    suspend fun getDirectors():Response<List<HomeRecylerViewItem.Director>>
}