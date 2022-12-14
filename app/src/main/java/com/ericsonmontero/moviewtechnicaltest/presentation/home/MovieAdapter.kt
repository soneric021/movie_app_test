package com.ericsonmontero.moviewtechnicaltest.presentation.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ericsonmontero.moviewtechnicaltest.databinding.ItemMovieBinding
import com.ericsonmontero.moviewtechnicaltest.domain.models.MovieModel

class MovieAdapter(private val listener:(MovieModel) -> Unit) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private val data:AsyncListDiffer<MovieModel> = AsyncListDiffer(this, MovieDiffCallBack())
    private lateinit var context: Context

    fun setData(list:List<MovieModel>){
       data.submitList(list)
    }
    
    fun getCurrentList():List<MovieModel> = data.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getCurrentList()[position]
        Glide.with(context).load(movie.image).into(holder.binding.imagePoster)
        holder.binding.tvTitle.text = movie.title
        holder.binding.tvReleaseDate.text = movie.releaseDate
        holder.binding.root.setOnClickListener {
            listener.invoke(movie)
        }
    }

    override fun getItemCount(): Int {
        return getCurrentList().size
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<MovieModel>(){
        override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
            return oldItem == newItem
        }

    }
    class MovieViewHolder(itemView: ItemMovieBinding) :RecyclerView.ViewHolder(itemView.root){
        val binding = itemView
    }
}