package com.devyash.multipleviewrecyclerview.repositories

import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import com.devyash.multipleviewrecyclerview.network.MovieApi
import com.devyash.multipleviewrecyclerview.network.MovieApiService
import retrofit2.Response

class HomeRepository {

    private val movieApi = MovieApiService.movieApi

    suspend fun getMovies(): Response<HomeRecylerViewItem.Movie> {
        return movieApi.getMovies()
    }

    suspend fun getDirectors(): Response<HomeRecylerViewItem.Director> {
        return movieApi.getDirectors()
    }
}