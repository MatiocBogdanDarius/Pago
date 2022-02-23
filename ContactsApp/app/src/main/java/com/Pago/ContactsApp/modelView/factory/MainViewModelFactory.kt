package com.Pago.ContactsApp.modelView.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.Pago.ContactsApp.model.repository.MainRepository
import com.Pago.ContactsApp.modelView.MainViewModel

class MainViewModelFactory constructor(private val repository: MainRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}