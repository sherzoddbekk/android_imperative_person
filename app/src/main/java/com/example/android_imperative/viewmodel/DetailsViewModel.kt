package com.example.android_imperative.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_imperative.model.TVShowDetails
import com.example.android_imperative.repository.TVShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(private val tvShowRepository: TVShowRepository): ViewModel() {
    val isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val showDetails = MutableLiveData<TVShowDetails>()

    fun apiTVShowDetails(show_id: Int){
        isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val response = tvShowRepository.apiTVShowDetails(show_id)
            withContext(Dispatchers.Main){
                if (response.isSuccessful){
                    showDetails.postValue(response.body())
                    isLoading.value = false
                } else{
                    onError("Error ${response.message()}")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        isLoading.value = false
    }
}