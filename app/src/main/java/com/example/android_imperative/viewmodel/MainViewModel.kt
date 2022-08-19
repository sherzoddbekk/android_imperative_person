package com.example.android_imperative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_imperative.model.TVShow
import com.example.android_imperative.model.TVShowDetails
import com.example.android_imperative.model.TVShowPopular
import com.example.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val tvShowRepository: TVShowRepository): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val tvShowsFromApi = MutableLiveData<ArrayList<TVShow>>()
    val tvShowsFromDB = MutableLiveData<ArrayList<TVShow>>()

    val tvShowPopular = MutableLiveData<TVShowPopular?>()
    val tvShowDetails = MutableLiveData<TVShowDetails>()

    /**
     * Retrofit Relates
     * */
    fun apiTVShowPopular(page: Int){
        isLoading.value = true
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = tvShowRepository.apiTVShowPopular(page)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    val resp = response.body()
                    tvShowPopular.postValue(resp)

                    tvShowsFromApi.postValue(resp!!.tv_shows)
                    isLoading.value = false
                } else{
                    onError("Error: ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }


    /**
     * Room Relates
     * */

    fun insertTvShowToDB(tvShow: TVShow){
        viewModelScope.launch {
            tvShowRepository.insertTVShowToDB(tvShow)
        }
    }

    fun getTvShowsFromDB(){
        viewModelScope.launch {
            tvShowsFromDB.postValue(tvShowRepository.getTvShowsFromDB() as ArrayList<TVShow>)
        }
    }

}