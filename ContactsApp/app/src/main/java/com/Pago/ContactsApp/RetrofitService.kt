package com.PagoContactsApp

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("users/{userId}/posts/")
    suspend fun getAllUserPosts(@Path("userId") userId: Int) : Response<List<ProfileModel>>

    @GET("users")
    suspend fun getAllContacts() : Response<List<Contact>>



    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://gorest.co.in/public/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }

    }
}