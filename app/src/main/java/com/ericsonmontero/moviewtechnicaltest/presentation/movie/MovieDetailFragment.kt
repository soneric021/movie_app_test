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
import com.ericsonmontero.moviewtechnicaltest.utils.Utils
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
        setupView()
    }

    private fun setupView() {
        Glide.with(requireContext()).load(navArgs.movie.image).into(binding.blurImage)
        Glide.with(requireContext())
            .load(navArgs.movie.image)
            .override(500,400)
            .dontTransform()
            .into(binding.posterImage)

        binding.tvName.text = navArgs.movie.title
        val runtimeHour = Utils.hoursFromMinuts(navArgs.movie.runtimeMins.toInt())
        binding.tvReleaseDate.text = getString(R.string.release,navArgs.movie.releaseDate, runtimeHour.toString() )
        binding.tvDescription.text = navArgs.movie.plot
        binding.tvCast.text = getString(R.string.cast_values, navArgs.movie.stars.joinToString { "$it" })

        val genres = navArgs.movie.genres.split(",")
        binding.tvGen1.text = genres[0]
        if(genres.size > 1){
            binding.tvGen2.text = genres[1]
        }else{
            binding.tvGen2.visibility = View.GONE
        }

        binding.tvImdb.text  = getString(R.string.imdb, navArgs.movie.imdbRating)
    }
}