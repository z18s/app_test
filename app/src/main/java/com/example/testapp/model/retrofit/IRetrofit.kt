package com.example.testapp.model.retrofit

import com.example.testapp.model.entity.Message
import io.reactivex.rxjava3.core.Single

interface IRetrofit {
    fun getText(): Single<Message>
}