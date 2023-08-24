package com.example.testapp.model.retrofit

import com.example.testapp.model.entity.Message
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataSource {
    @GET("api?format=json")
    fun loadText(): Single<Message>
}