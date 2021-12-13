package com.baharudin.imovie.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.baharudin.imovie.R
import com.baharudin.imovie.databinding.FragmentDetailBinding
import com.baharudin.imovie.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding : FragmentDetailBinding ?= null
    private val binding get() = _binding!!
    val args by navArgs<DetailFragmentArgs>()
    private val movieViewModel : MovieViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentDetailBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)


    }
    private fun getData() {
        val movie = args.movie

    }
}