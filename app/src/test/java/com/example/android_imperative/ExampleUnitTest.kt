package com.example.android_imperative

import android.util.Log
import com.example.android_imperative.di.AppModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import kotlin.math.log

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun checkStatusCode() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        assertEquals(response.code(),200)
    }

    @Test
    fun checkTVShowListNotNull() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(35624)
        assertNotNull(response.body())
        assertNotNull(response.body()!!.tv_shows)
    }

    @Test
    fun checkTVShowListSize() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(35624)
        val tvShowPopular = response.body()
        assertEquals(tvShowPopular!!.tv_shows.size,4)
    }

    @Test
    fun checkTVShowStatus() = runTest {
        val response = AppModule().tvShowService().apiTVShowPopular(35624)
        val tvShowPopular = response.body()
        val tvShows = tvShowPopular!!.tv_shows
        val tvShow = tvShows.first()
        assertEquals(tvShow.status,"Running")
    }

    //homework

    @Test
    fun checkTVShowCountryName() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        assertEquals(tvShow.country,"US")
    }

    @Test
    fun checkTVShowGenresSize() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        val size = tvShow.genres.size
        assertEquals(size,3)
    }
    @Test
    fun checkTVShowPictureSize() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        val size = tvShow.pictures.size
        assertEquals(size,7)
    }

    @Test
    fun checkTVShowEndDateNotNull() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        assertNull(tvShow.endDate)
    }

    @Test
    fun checkTVShowRatingCount() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        assertEquals(tvShow.rating_count, "1569")
    }
    @Test
    fun checkTVShowEpisodesSize() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        val size = tvShow.episodes.size
        assertEquals(size, 171)

    }

    @Test
    fun checkTVShowEpisodeName() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        val episode = tvShow.episodes.first()
        assertEquals(episode.name, "Pilot")

    }
    @Test
    fun checkTVShowEpisodeAirDate() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        val episode = tvShow.episodes.first()
        assertEquals(episode.air_date, "2014-10-08 00:00:00")

    }


    @Test
    fun checkTVShowEpisodeSeasonSize() = runTest {
        val response = AppModule().tvShowService().apiTVShowDetails(35624)
        val tvShow = response.body()!!.tvShow
        val episode = tvShow.episodes.first()
        val season = episode.season
        assertEquals(season, 1)
    }

}