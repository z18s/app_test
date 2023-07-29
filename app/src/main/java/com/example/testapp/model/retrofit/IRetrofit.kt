package com.example.testapp.model.retrofit

import com.example.testapp.model.entity.Message
import retrofit2.Call

interface IRetrofit {
    fun getText(): Call<Message>
}