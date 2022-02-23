package com.PagoContactsApp

class ProfileRepository constructor(private val retrofitService: RetrofitService) {

    suspend fun getAllUserPosts(userId: Int) = retrofitService.getAllUserPosts(userId)

}