package com.PagoContactsApp
class MainRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllContacts() = retrofitService.getAllContacts()

}