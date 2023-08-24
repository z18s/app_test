package com.example.testapp.model.retrofit

import com.example.testapp.model.entity.Message
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitConnection(private var dataSource: IDataSource) : IRetrofit {

    override fun getText(): Single<Message> = dataSource.loadText().subscribeOn(Schedulers.io())
}