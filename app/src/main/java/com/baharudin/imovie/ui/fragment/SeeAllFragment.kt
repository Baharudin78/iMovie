package com.baharudin.imovie.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudin.imovie.R
import com.baharudin.imovie.adapter.MovieLoadStateAdapter
import com.baharudin.imovie.adapter.MoviePagingAdapter
import com.baharudin.imovie.adapter.MovieSeeAllAdapter
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.databinding.FragmentSeeAllBinding
import com.baharudin.imovie.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SeeAllFragment : Fragment(R.layout.fragment_see_all), MovieSeeAllAdapter.OnClickItemListener {

    private var _binding : FragmentSeeAllBinding? = null
    private val binding get() = _binding!!
    private lateinit var movieAdapter : MovieSeeAllAdapter
    private val movieViewModel : MovieViewModel by activityViewModels()
    private var nowPlayingJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentSeeAllBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieSeeAllAdapter(this)

        setupRecycleview()
        getData()
    }
    private fun getData() {
        nowPlayingJob?.cancel()
        nowPlayingJob = lifecycleScope.launch {
            movieViewModel.getNowPlaying().collectLatest {
                movieAdapter.submitData(it)
            }
        }
    }
    private fun setupRecycleview() {
        binding.apply {
            rvSeenowPlaying.setHasFixedSize(true)
            rvSeenowPlaying.layoutManager = LinearLayoutManager(requireContext())
            rvSeenowPlaying.adapter = movieAdapter.withLoadStateFooter(
                footer = MovieLoadStateAdapter{retry()}
            )
            movieAdapter.addLoadStateListener {  loadState ->
                if (loadState.mediator?.refresh is LoadState.Loading) {
                    if (movieAdapter.snapshot().isEmpty()) {
                        binding.progressBar2.isVisible = true
                    }
                }else {
                    binding.progressBar2.isVisible = false
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
                        if(movieAdapter.snapshot().isEmpty()) {
                            binding.tvError.isVisible = true
                            binding.tvError.text = it.error.localizedMessage
                        }
                    }
                }
            }
        }
    }
    private fun retry() {
        movieAdapter.retry()
    }

    override fun onClickItem(movie: Movie) {
        val action = SeeAllFragmentDirections.actionSeeAllFragmentToDetailFragment(movie)
        findNavController().navigate(action)
    }
}