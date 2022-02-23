package com.Pago.ContactsApp.modelView

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.Pago.ContactsApp.model.dataModel.ProfileModel
import com.Pago.ContactsApp.model.repository.ProfileRepository
import kotlinx.coroutines.*

class ProfileViewModel
    constructor(private val profileRepository: ProfileRepository, private var userId : Int) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val userPostsList = MutableLiveData<List<ProfileModel>>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    private val loading = MutableLiveData<Boolean>()

    fun getAllUserPosts() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            loading.postValue(true)
            val response = profileRepository.getAllUserPosts(userId)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    userPostsList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

}