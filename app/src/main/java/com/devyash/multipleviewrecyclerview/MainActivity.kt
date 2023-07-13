package com.devyash.multipleviewrecyclerview

import HomeViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyash.multipleviewrecyclerview.adapters.HomeRecylerViewAdapter
import com.devyash.multipleviewrecyclerview.databinding.ActivityMainBinding
import com.devyash.multipleviewrecyclerview.models.HomeRecylerViewItem
import com.devyash.multipleviewrecyclerview.network.NetworkResult

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeAdapter: HomeRecylerViewAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        setupRecylerView()

        homeAdapter.itemClickListener = {view, item, position ->  
            val message = when(item){
                is HomeRecylerViewItem.Director -> "Director Clicked"
                is HomeRecylerViewItem.Movie -> "Movie Clicked"
                is HomeRecylerViewItem.Title -> "Title Clicked"
            }
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        viewModel.homeListItems.observe(this, Observer { result ->
            when (result) {
                is NetworkResult.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(
                        this@MainActivity,
                        "Something Went Wrong!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is NetworkResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is NetworkResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    homeAdapter.setData(result.data!!)
                }
            }
        })
    }

    private fun setupRecylerView() {
        homeAdapter = HomeRecylerViewAdapter()
        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = homeAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        binding.recyclerView.adapter = null
    }
}