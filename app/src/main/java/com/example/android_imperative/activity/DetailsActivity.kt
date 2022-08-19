package com.example.android_imperative.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.android_imperative.adapter.EpisodesAdapter
import com.example.android_imperative.adapter.TVShortAdapter
import com.example.android_imperative.databinding.ActivityDetailsBinding
import com.example.android_imperative.model.Details
import com.example.android_imperative.model.Episode
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.DetailsViewModel
import com.google.android.material.tabs.TabLayout
import java.util.*

class DetailsActivity : BaseActivity() {
    private val TAG = this::class.java.simpleName
    private lateinit var binding: ActivityDetailsBinding
    private val viewModel by viewModels<DetailsViewModel>()
    private val tvShortAdapter by lazy { TVShortAdapter() }
    private val episodesAdapter by lazy { EpisodesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObserves()
    }

    private fun initViews() {
        binding.rvShorts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvShorts.adapter = tvShortAdapter

        binding.rvEpisodes.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvEpisodes.adapter = episodesAdapter

        binding.ivClose.setOnClickListener {
            ActivityCompat.finishAfterTransition(this)
        }

        val extras = intent.extras
        val show_id = extras!!.getLong("show_id")
        val show_img = extras.getString("show_img")
        val show_name = extras.getString("show_name")
        val show_network = extras.getString("show_network")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras.getString("iv_movie")
            binding.ivDetails.transitionName = imageTransitionName
        }

        binding.tvName.text = show_name
        binding.tvType.text = show_network
        Glide.with(this).load(show_img).into(binding.ivDetails)

        viewModel.apiTVShowDetails(show_id.toInt())
    }

    private fun initObserves() {
        viewModel.showDetails.observe(this) {
            Logger.d(TAG, it.toString())
            tvShortAdapter.submitList(it.tvShow.pictures)
            getAllSeasons(it.tvShow)
            binding.tvDetails.text = it.tvShow.description
        }

        viewModel.errorMessage.observe(this) {
            Logger.d(TAG, it.toString())
        }

        viewModel.isLoading.observe(this) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }
    }

    private fun getAllSeasons(tvShow: Details){
        val map = TreeMap<String, ArrayList<Episode>>()

        Logger.d("@@@", tvShow.episodes.toString())
        for (i in tvShow.episodes){
            val items = map.getOrDefault("Season ${i.season.toString()}", ArrayList())
            items.add(i)
            map.put("Season ${i.season.toString()}", items)
        }

        for(k in map.keys){
            binding.tabSeasons.addTab(binding.tabSeasons.newTab().setText(k))
        }

        episodesAdapter.submitList(map["Season ${tvShow.episodes[0].season}"])

        binding.tabSeasons.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                episodesAdapter.submitList(map[tab?.text])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        Logger.d("@@@", "Map: ${map.size}")

    }
}