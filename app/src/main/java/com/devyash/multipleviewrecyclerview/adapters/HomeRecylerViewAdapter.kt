package com.devyash.multipleviewrecyclerview.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.devyash.multipleviewrecyclerview.R
import com.devyash.multipleviewrecyclerview.databinding.ItemDirectorBinding
import com.devyash.multipleviewrecyclerview.databinding.ItemMovieBinding
import com.devyash.multipleviewrecyclerview.databinding.ItemTitleBinding
import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import com.devyash.multipleviewrecyclerview.others.HomeDiffUtil

class HomeRecylerViewAdapter : RecyclerView.Adapter<HomeRecylerViewHolder>() {

    var items = listOf<HomeRecylerViewItem>()

    fun setData(newData: List<HomeRecylerViewItem>) {
        val homeDiffUtil = HomeDiffUtil(items, newData)
        val diffUtilResult = DiffUtil.calculateDiff(homeDiffUtil)
        items = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecylerViewHolder {
        return when (viewType) {
            R.layout.item_title -> HomeRecylerViewHolder.TitleViewHolder(
                ItemTitleBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            R.layout.item_movie -> HomeRecylerViewHolder.MoviesViewHolder(
                ItemMovieBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            R.layout.item_director -> HomeRecylerViewHolder.DirectorViewHolder(
                ItemDirectorBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> {
                throw IllegalArgumentException("Invalid ViewType Provided")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: HomeRecylerViewHolder, position: Int) {
        when (holder) {
            is HomeRecylerViewHolder.DirectorViewHolder -> {
                holder.bind(items[position] as HomeRecylerViewItem.Director)
            }

            is HomeRecylerViewHolder.MoviesViewHolder -> {
                holder.bind(items[position] as HomeRecylerViewItem.Movie)
            }

            is HomeRecylerViewHolder.TitleViewHolder -> {
                holder.bind(items[position] as HomeRecylerViewItem.Title)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeRecylerViewItem.Director -> R.layout.item_director
            is HomeRecylerViewItem.Movie -> R.layout.item_movie
            is HomeRecylerViewItem.Title -> R.layout.item_title
        }
    }

}