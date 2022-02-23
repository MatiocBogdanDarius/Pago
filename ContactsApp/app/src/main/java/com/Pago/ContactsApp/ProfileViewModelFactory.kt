package com.PagoContactsApp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProfileViewModelFactory
    constructor(private val repository: ProfileRepository, var userId: Int)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(this.repository, userId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}