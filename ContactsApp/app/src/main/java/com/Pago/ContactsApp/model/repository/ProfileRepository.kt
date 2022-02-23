package com.Pago.ContactsApp.model.repository

import com.Pago.ContactsApp.model.RetrofitService

class ProfileRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllUserPosts(userId: Int) = retrofitService.getAllUserPosts(userId)

}