package com.devyash.multipleviewrecyclerview.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import com.devyash.multipleviewrecyclerview.network.NetworkResult
import com.devyash.multipleviewrecyclerview.repositories.HomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel:ViewModel() {

    private lateinit var homeRepository:HomeRepository

    private val _homeListItems = MutableStateFlow<NetworkResult<List<HomeRecylerViewItem>>?>(null)
    val homeListItems:StateFlow<NetworkResult<List<HomeRecylerViewItem>>?> get() = _homeListItems

    init {
        homeRepository = HomeRepository()
        getHomeListItems()
    }

    private fun getHomeListItems()=viewModelScope.launch {
        _homeListItems.value = NetworkResult.Loading()

        try {
            val moviesDeffered = async { homeRepository.getMovies() }
            val directorsDiffered =  async { homeRepository.getDirectors() }

            val movies = moviesDeffered.await()
            val directors = directorsDiffered.await()

            val homeItemsList = mutableListOf<HomeRecylerViewItem>()
            if(movies is NetworkResult.Success<*> && directors is NetworkResult.Success<*>){
                homeItemsList.add(HomeRecylerViewItem.Title(1,"Recommended Movies"))
                homeItemsList.addAll(arrayListOf(movies.body()!!))
                homeItemsList.add(HomeRecylerViewItem.Title(2,"Top Directors"))
                homeItemsList.addAll(arrayListOf(directors.body()!!))

                _homeListItems.value = NetworkResult.Success(homeItemsList)
            }else{
                _homeListItems.value = NetworkResult.Error("",null)
            }

        }catch (e:Exception){
            _homeListItems.value = NetworkResult.Error(e.message)
        }
    }

}