package com.example.testapp.model.retrofit

import com.example.testapp.model.entity.Message
import retrofit2.Call
import retrofit2.http.GET

interface IDataSource {
    @GET("api?format=json")
    fun loadText(): Call<Message>
}