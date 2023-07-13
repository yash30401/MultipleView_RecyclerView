package com.devyash.multipleviewrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.devyash.multipleviewrecyclerview.adapters.HomeRecylerViewAdapter
import com.devyash.multipleviewrecyclerview.databinding.ActivityMainBinding
import com.devyash.multipleviewrecyclerview.network.NetworkResult
import com.devyash.multipleviewrecyclerview.viewmodels.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.homeListItems.collect { result ->
                when (result) {
                    is NetworkResult.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
                    }

                    is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is NetworkResult.Success ->{
                        binding.progressBar.visibility =View.GONE
                        homeAdapter.items = result.data!!
                    }
                    else->{
                        Log.d("TEST","Something Went Wrong!")
                    }
                }
            }
        }

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
    }
}