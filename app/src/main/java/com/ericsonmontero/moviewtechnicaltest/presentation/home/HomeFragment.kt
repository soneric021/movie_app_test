package com.ericsonmontero.moviewtechnicaltest.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ericsonmontero.moviewtechnicaltest.R
import com.ericsonmontero.moviewtechnicaltest.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding:FragmentHomeBinding? = null
    private val binding:FragmentHomeBinding
    get() = _binding!!
    private lateinit var movieAdapter:MovieAdapter

    private val viewModel:MovieViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        movieAdapter = MovieAdapter {
            findNavController().navigate(HomeFragmentDirections.actionToFragmentMovieDetail(it))
        }
        setupRecyclerView()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.movies.collect{
                    when(it){
                        is UiState.Error -> {
                            Snackbar.make(binding.root, it.exception, Snackbar.LENGTH_LONG).show()
                        }
                        is UiState.Success -> {
                            movieAdapter.setData(it.news)
                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.rvMovies.apply {
            adapter = movieAdapter
            layoutManager = GridLayoutManager(requireContext(),3)
        }
    }
}