package com.Pago.ContactsApp.modelView.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Pago.ContactsApp.model.repository.ProfileRepository
import com.Pago.ContactsApp.modelView.ProfileViewModel

class ProfileViewModelFactory
    constructor(private val repository: ProfileRepository, private var userId: Int)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(this.repository, userId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}