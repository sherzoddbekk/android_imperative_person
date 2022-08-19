package com.example.android_imperative.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_imperative.model.TVShow


@Dao
interface TVshowDao {

    @Query("SELECT * FROM tv_show")
    suspend fun getShowsFromDB(): List<TVShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTVVShowToDB(tvShow: TVShow)

    @Query("DELETE FROM tv_show")
    suspend fun deleteShowsFromDB()



}