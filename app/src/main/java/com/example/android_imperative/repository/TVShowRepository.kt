package com.example.android_imperative.repository

import com.example.android_imperative.db.TVshowDao
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.network.TVShowService
import javax.inject.Inject

class TVShowRepository @Inject constructor(private val tvShowService: TVShowService
,private val tVshowDao: TVshowDao) {

    //retrofit
    suspend fun apiTVShowPopular(page:Int) = tvShowService.apiTVShowPopular(page)
    suspend fun apiTVShowDetails(q:Int) = tvShowService.apiTVShowDetails(q)

    //Room

    suspend fun getTvShowsFromDB() = tVshowDao.getShowsFromDB()
    suspend fun insertTVShowToDB(tvShow: TVShow) = tVshowDao.insertTVVShowToDB(tvShow)
    suspend fun deleteTVShowsFromDB() = tVshowDao.deleteShowsFromDB()


}