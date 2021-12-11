package com.baharudin.imovie.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.imovie.R
import com.baharudin.imovie.adapter.MovieLoadStateAdapter
import com.baharudin.imovie.adapter.MoviePagingAdapter
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.databinding.FragmentHomeBinding
import com.baharudin.imovie.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), MoviePagingAdapter.OnClickItemListener {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val movieViewModel : MovieViewModel by activityViewModels()
    private lateinit var moviePagingAdapter: MoviePagingAdapter
    private var nowPlayingJob: Job? = null



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentHomeBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

       moviePagingAdapter = MoviePagingAdapter(this)
        setupRecycleview()
        startJob()

        binding.btRetry.setOnClickListener {
            moviePagingAdapter.refresh()
        }



    }

    private fun startJob() {
        nowPlayingJob?.cancel()
        nowPlayingJob = lifecycleScope.launch {
            movieViewModel.getNowPlaying().collectLatest {
                moviePagingAdapter.submitData(it)
            }
        }
    }
    private fun setupRecycleview() {
        binding.apply {
            rvNowPlaying.setHasFixedSize(true)
            rvNowPlaying.layoutManager = LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false)
            rvNowPlaying.adapter = moviePagingAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter {retry()}
            )
            moviePagingAdapter.addLoadStateListener { loadState ->
                if (loadState.mediator?.refresh is LoadState.Loading) {
                    if (moviePagingAdapter.snapshot().isEmpty()) {
                        binding.progressBar.isVisible = true
                    }
                    binding.textView.isVisible = false
                }else {
                    binding.progressBar.isVisible = false
                    val error = when {
                        loadState.mediator?.prepend is LoadState.Error ->
                            loadState.mediator?.prepend as LoadState.Error
                        loadState.mediator?.append is LoadState.Error ->
                            loadState.mediator?.append as LoadState.Error
                        loadState.mediator?.refresh is LoadState.Error ->
                            loadState.mediator?.refresh as LoadState.Error

                        else -> null
                    }
                    error?.let {
                        if (moviePagingAdapter.snapshot().isEmpty()) {
                            binding.tvError.isVisible = true
                            binding.tvError.text = it.error.localizedMessage
                        }
                    }
                }
            }
        }
    }
    private fun retry() {
        moviePagingAdapter.retry()
    }

    override fun onClickItem(movie: Movie) {

    }
}