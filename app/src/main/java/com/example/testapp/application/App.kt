package com.example.testapp.application

import android.app.Application
import com.example.testapp.model.retrofit.IDataSource
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    val dataSource: IDataSource = Retrofit.Builder()
        .baseUrl(APP_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(IDataSource::class.java)

    companion object {
        private const val APP_URL = "https://geek-jokes.sameerkumar.website/"

        @Volatile private var INSTANCE: App? = null

        fun getInstance(): App = INSTANCE ?: synchronized(this) { App().also { INSTANCE = it } }
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}