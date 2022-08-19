package com.example.android_imperative.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.android_imperative.R
import com.example.android_imperative.activity.DetailsActivity
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.FragmentHistoryBinding
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history) {
    private val TAG = this::class.java.simpleName
    private val binding by viewBinding(FragmentHistoryBinding::bind)
    private val viewModel by viewModels<MainViewModel>()

    private val adapterTvShowAdapter by lazy { TVShowAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getTvShowsFromDB()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserves()
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHistory.layoutManager = gridLayoutManager
        binding.rvHistory.adapter = adapterTvShowAdapter

        adapterTvShowAdapter.onClick = { tvShow, ivMovie ->
//            callDetailsActivity(tvShow, ivMovie)
        }

        binding.bFab.setOnClickListener {
            binding.rvHistory.smoothScrollToPosition(0)
        }

    }

    private fun initObserves() {
        // Retrofit
//        viewModel.tvShowsFromApi.observe(viewLifecycleOwner) {
//            Logger.d(TAG, it.size.toString())
//            adapterTvShowAdapter.submitList(it)
//        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
        }
        viewModel.isLoading.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.toString())
            if (it) {
                binding.pbLoading.visibility = View.VISIBLE
            } else {
                binding.pbLoading.visibility = View.GONE
            }
        }

        // Room

        viewModel.tvShowsFromDB.observe(viewLifecycleOwner){
            if (it.size > 0){
                binding.tvEmptyMessage.visibility = View.GONE
            } else{
                binding.tvEmptyMessage.visibility = View.VISIBLE
            }
            adapterTvShowAdapter.submitList(it)
        }
    }

    private fun callDetailsActivity(tvShow: TVShow, sharedImageView: ImageView) {
        val intent = Intent(requireContext(), DetailsActivity::class.java)
        intent.putExtra("show_id", tvShow.id)
        intent.putExtra("show_img", tvShow.image_thumbnail_path)
        intent.putExtra("show_name", tvShow.name)
        intent.putExtra("show_network", tvShow.network)
        intent.putExtra("iv_movie", ViewCompat.getTransitionName(sharedImageView))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(),
            sharedImageView,
            ViewCompat.getTransitionName(sharedImageView)!!
        )

        startActivity(intent, options.toBundle())
    }

}