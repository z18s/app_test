package com.example.testapp.model.retrofit

import com.example.testapp.model.entity.Message
import retrofit2.Call

class RetrofitConnection(private var dataSource: IDataSource) : IRetrofit {

    override fun getText(): Call<Message> = dataSource.loadText()
}