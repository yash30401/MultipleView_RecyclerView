package com.devyash.multipleviewrecyclerview.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.devyash.multipleviewrecyclerview.databinding.ItemDirectorBinding
import com.devyash.multipleviewrecyclerview.databinding.ItemMovieBinding
import com.devyash.multipleviewrecyclerview.databinding.ItemTitleBinding
import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem

sealed class HomeRecylerViewHolder(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root) {

    var itemClickListener: ((view: View, item: HomeRecylerViewItem, position: Int) -> Unit)? = null

    class TitleViewHolder(private val binding:ItemTitleBinding):HomeRecylerViewHolder(binding){
        fun bind(title:HomeRecylerViewItem.Title){
            binding.textViewTitle.text = title.title
            binding.textViewAll.setOnClickListener {
                itemClickListener?.invoke(it,title,adapterPosition)
            }
        }
    }

    class MoviesViewHolder(private val binding: ItemMovieBinding):HomeRecylerViewHolder(binding){
        fun bind(movie:HomeRecylerViewItem.Movie){
            Glide.with(binding.root).load(movie.thumbnail).into(binding.ivMoviePoster)
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it,movie,adapterPosition)
            }
        }
    }

    class DirectorViewHolder(private val binding: ItemDirectorBinding):HomeRecylerViewHolder(binding){
        fun bind(director:HomeRecylerViewItem.Director){
            Glide.with(binding.root).load(director.avatar).into(binding.imageViewDirector)
            binding.textViewName.text =  director.name
            binding.textViewMovies.text = "${director.movie_count} Movies"
            binding.root.setOnClickListener {
                itemClickListener?.invoke(it,director,adapterPosition)
            }
        }
    }

}