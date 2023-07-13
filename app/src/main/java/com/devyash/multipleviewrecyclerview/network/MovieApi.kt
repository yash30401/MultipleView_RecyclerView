package com.devyash.multipleviewrecyclerview.network

import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import retrofit2.http.GET

interface MovieApi {

    @GET("/movies")
    suspend fun getMovies():List<HomeRecylerViewItem.Movie>

    @GET("/directors")
    suspend fun getDirectors():List<HomeRecylerViewItem.Director>
}