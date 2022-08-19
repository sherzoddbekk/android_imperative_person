package com.example.android_imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android_imperative.R
import com.example.android_imperative.activity.MainActivity
import com.example.android_imperative.databinding.ItemTvShowBinding
import com.example.android_imperative.model.TVShow

class TVShowAdapter: ListAdapter<TVShow, TVShowAdapter.TvShowHolder>(ITEM_DIF) {

    var onClick: ((TVShow, ImageView) -> Unit)? = null

    companion object{
        val ITEM_DIF = object : DiffUtil.ItemCallback<TVShow>(){
            override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
                return oldItem == newItem
            }

        }
    }

    fun submitData(list: List<TVShow>){
        val items = ArrayList<TVShow>()
        items.addAll(currentList)
        items.addAll(list)
        submitList(items)
    }

    inner class TvShowHolder(private val binding: ItemTvShowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val tvShow = getItem(adapterPosition)
            with(binding){

                ViewCompat.setTransitionName(ivMovie, tvShow.name)

                Glide.with(root).load(tvShow.image_thumbnail_path).into(ivMovie)
                tvName.text = tvShow.name
                tvType.text = tvShow.network

                ivMovie.setOnClickListener {
                    // Save TVShow into Room


                    // Call Details Activity
                    onClick?.invoke(tvShow, ivMovie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        return TvShowHolder(ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        holder.bind()
    }
}