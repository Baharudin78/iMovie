package com.baharudin.imovie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.baharudin.imovie.R
import com.baharudin.imovie.data.remote.model.Movie
import com.baharudin.imovie.databinding.ItemNowPlayingBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class MoviePagingAdapter(private val listener :OnClickItemListener ) : PagingDataAdapter<Movie, MoviePagingAdapter.MovieViewHlder>(MovieComparator) {

    inner class MovieViewHlder(
        val binding : ItemNowPlayingBinding
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
                        .into(ivToprated)
                    tvJudul.text = movie.original_title
                }
            }
        }

    object MovieComparator: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: MovieViewHlder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bindItem(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHlder {
        val inflated = ItemNowPlayingBinding.inflate(
            LayoutInflater.from(
                parent.context
            ),
            parent, false
        )
        return MovieViewHlder(inflated)
    }
    interface OnClickItemListener{
        fun onClickItem(movie: Movie)
    }
}