package com.example.android_imperative.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android_imperative.databinding.ItemEpisodeBinding
import com.example.android_imperative.model.Episode

class EpisodesAdapter: ListAdapter<Episode, EpisodesAdapter.TvShowHolder>(ITEM_DIF) {

    var onClick: ((Episode, ImageView) -> Unit)? = null

    companion object{
        val ITEM_DIF = object : DiffUtil.ItemCallback<Episode>(){
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class TvShowHolder(private val binding: ItemEpisodeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(){
            val episode = getItem(adapterPosition)
            with(binding){

                tvEpisodeName.text = "Episode ${episode.episode}: ${episode.name}"
                tvEpisodeAirDate.text = "Air Date: ${episode.air_date}"

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowHolder {
        return TvShowHolder(ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TvShowHolder, position: Int) {
        holder.bind()
    }
}