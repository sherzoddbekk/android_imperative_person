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
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.android_imperative.R
import com.example.android_imperative.activity.DetailsActivity
import com.example.android_imperative.adapter.TVShowAdapter
import com.example.android_imperative.databinding.FragmentHomeBinding
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.utils.Logger
import com.example.android_imperative.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val TAG = this::class.java.simpleName

    private val viewModel: MainViewModel by viewModels()
    private val adapterTvShowAdapter by lazy { TVShowAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apiTVShowPopular(1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObserves()
    }

    private fun initViews() {
        val gridLayoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvHome.layoutManager = gridLayoutManager
        binding.rvHome.adapter = adapterTvShowAdapter

        adapterTvShowAdapter.onClick = { tvShow, ivMovie ->
            callDetailsActivity(tvShow, ivMovie)
            viewModel.insertTvShowToDB(tvShow)
        }

        binding.rvHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == adapterTvShowAdapter.itemCount - 1) {
                    val nextPage = viewModel.tvShowPopular.value!!.page + 1
                    val totalPages = viewModel.tvShowPopular.value!!.pages
                    if (nextPage <= totalPages) {
                        viewModel.apiTVShowPopular(nextPage)
                    }
                }
            }
        })

        binding.bFab.setOnClickListener {
            binding.rvHome.smoothScrollToPosition(0)
        }

//        viewModel.apiTVShowPopular(1)
    }

    private fun initObserves() {
        // Retrofit
        viewModel.tvShowsFromApi.observe(viewLifecycleOwner) {
            Logger.d(TAG, it.size.toString())
            adapterTvShowAdapter.submitData(it)
        }
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