package com.baharudin.imovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.imovie.R
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.databinding.ItemSeeAllPlayingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MovieSeeAllAdapter(private val listener : OnClickItemListener) : PagingDataAdapter<Movie, MovieSeeAllAdapter.SeeAllHolder>(ComparatorDiffUtil()) {

    inner class SeeAllHolder(val binding : ItemSeeAllPlayingBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onClickItem(item)
                    }
                }
            }
        }
        fun bindItem(movie: Movie) {
            binding.apply {
                Glide.with(itemView)
                    .load("${movie.baseUrl}${movie.poster_path}")
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_error)
                    .into(ivPoster)
                tvJudul.text = movie.original_title
                tvRelease.text = movie.release_date
            }
        }
    }

    private class ComparatorDiffUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: SeeAllHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bindItem(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeeAllHolder {
        val inflater = ItemSeeAllPlayingBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return SeeAllHolder(inflater)
    }
    interface OnClickItemListener{
        fun onClickItem(movie: Movie)
    }
}