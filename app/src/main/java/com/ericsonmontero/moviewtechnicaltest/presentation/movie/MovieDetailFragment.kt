package com.ericsonmontero.moviewtechnicaltest.presentation.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ericsonmontero.moviewtechnicaltest.R
import com.ericsonmontero.moviewtechnicaltest.databinding.FragmentHomeBinding
import com.ericsonmontero.moviewtechnicaltest.databinding.FragmentMovieDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding: FragmentMovieDetailBinding
        get() = _binding!!
    private val navArgs:MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(requireContext()).load(navArgs.movie.image).into(binding.blurImage)
        Glide.with(requireContext()).load(navArgs.movie.image).into(binding.posterImage)
        binding.tvName.text = navArgs.movie.title
        binding.tvRelease.text = navArgs.movie.releaseDate
        binding.tvDescription.text = navArgs.movie.plot
        binding.tvCast.text = "Cast: ${navArgs.movie.stars.joinToString { "$it" }}"
    }
}