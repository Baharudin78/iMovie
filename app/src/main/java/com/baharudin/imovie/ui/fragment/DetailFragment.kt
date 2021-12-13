package com.baharudin.imovie.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.baharudin.imovie.R
import com.baharudin.imovie.databinding.FragmentDetailBinding
import com.baharudin.imovie.ui.viewmodel.MovieViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding : FragmentDetailBinding ?= null
    private val binding get() = _binding!!
    val args by navArgs<DetailFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDetailBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        getData()
    }
    private fun getData() {
        val movie = args.movie
        binding.apply {
            Glide.with(this@DetailFragment)
                .load("${movie.baseUrl}${movie.backdrop_path}")
                .centerCrop()
                .into(ivBackdop)
            Glide.with(this@DetailFragment)
                .load("${movie.baseUrl}${movie.poster_path}")
                .into(ivPoster)
            tvJudul.text = movie.original_title
            tvRelease.text = movie.release_date
            tvDesc.text = movie.overview
        }

    }
}