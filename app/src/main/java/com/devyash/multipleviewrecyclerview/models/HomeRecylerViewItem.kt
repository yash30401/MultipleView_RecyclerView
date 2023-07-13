package com.devyash.multipleviewrecyclerview.models

sealed class HomeRecylerViewItem {

    class Title(
        val id: Int,
        val title: String
    ) : HomeRecylerViewItem()

    class Movie(
        val id: Int,
        val title: String,
        val thumbnail: String,
        val releaseDate: String
    ) : HomeRecylerViewItem()

    class Director(
        val id: Int,
        val name: String,
        val avatar: String,
        val movie_count: Int
    ) : HomeRecylerViewItem()
}