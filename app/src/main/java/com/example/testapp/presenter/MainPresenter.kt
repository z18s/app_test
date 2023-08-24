package com.example.testapp.presenter

import android.os.Handler
import android.os.Looper
import com.example.testapp.model.entity.Message
import com.example.testapp.model.entity.RoomMessage
import com.example.testapp.model.retrofit.IRetrofit
import com.example.testapp.model.room.ICache
import com.example.testapp.presenter.rv.RvPresenter
import com.example.testapp.view.IMainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private var retrofit: IRetrofit, private val cache: ICache) : IMainPresenter {

    private var view: IMainView? = null
    override val rvPresenter = RvPresenter()

    override fun attachView(view: IMainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun initRvData() = setRvData(getAllCache())

    override fun onQueryClick() {
        getText()
    }

    override fun onDeleteClick() {
        clearCache()
    }

    private fun getText() = retrofit.getText().enqueue(object : Callback<Message> {
        override fun onResponse(call: Call<Message>, response: Response<Message>) {
            view?.apply {
                if (response.isSuccessful) {
                    val str = response.body()?.joke.toString()
                    setText(str)
                    setToCache(str)
                } else {
                    showToast(response.code().toString())
                }
            }
        }
        override fun onFailure(call: Call<Message>, t: Throwable) {
            view?.showToast(t.message ?: "Some error")
        }
    })

    private fun setToCache(str: String) {
        Handler(Looper.getMainLooper()).post {
            cache.insert(RoomMessage(0, str))
        }
        setRvData(getAllCache())
    }

    private fun getAllCache(): List<String> {
        val list = mutableListOf<String>()
        val cacheList = Handler(Looper.getMainLooper())
            .runCatching { cache.getAll() }
            .getOrDefault(listOf())
        cacheList.forEach { list.add(it.joke) }
        return list
    }

    private fun clearCache() {
        Handler(Looper.getMainLooper()).post {
            cache.deleteAll()
        }
        setRvData(listOf())
    }

    private fun setRvData(list: List<String>) {
        rvPresenter.updateData(list)
        view?.updateRv()
    }
}