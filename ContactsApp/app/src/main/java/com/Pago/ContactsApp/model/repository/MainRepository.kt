package com.Pago.ContactsApp.model.repository

import com.Pago.ContactsApp.model.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllContacts() = retrofitService.getAllContacts()

}