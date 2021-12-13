package com.baharudin.imovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.imovie.databinding.ItemNetworkStateBinding

class MovieLoadStateAdapter(private val retry : () -> Unit)
    : LoadStateAdapter<MovieLoadStateAdapter.MovieLoadHolder>(){

        inner class MovieLoadHolder(val binding : ItemNetworkStateBinding) :
                RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: MovieLoadHolder, loadState: LoadState) {
        val progressBar = holder.binding.progressBarItem
        val retryBtn = holder.binding.retyBtn
        val errorMessage = holder.binding.errorMsgItem

        if (loadState is LoadState.Loading) {
            progressBar.isVisible = true
            errorMessage.isVisible = false
            retryBtn.isVisible = false
        }else {
            progressBar.isVisible = false
            retryBtn.isVisible = false
        }
        if (loadState is LoadState.Error) {
            errorMessage.isVisible = true
            retryBtn.isVisible = true
            errorMessage.text = loadState.error.localizedMessage
        }
        retryBtn.setOnClickListener {
            retry.invoke()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): MovieLoadHolder {
        return MovieLoadHolder(
            ItemNetworkStateBinding.inflate(LayoutInflater.from(
                parent.context
            ), parent, false)
        )
    }
}