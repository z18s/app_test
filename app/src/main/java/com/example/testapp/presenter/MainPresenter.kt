package com.example.testapp.presenter

import android.annotation.SuppressLint
import com.example.testapp.model.entity.RoomMessage
import com.example.testapp.model.retrofit.IRetrofit
import com.example.testapp.model.room.ICache
import com.example.testapp.view.IMainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MainPresenter(private var retrofit: IRetrofit, private val cache: ICache) : IMainPresenter {

    private var view: IMainView? = null

    override fun attachView(view: IMainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onQueryClick() {
        getText()
    }

    @SuppressLint("CheckResult")
    override fun onDeleteClick() {
        clearCache().subscribe {
            updateHistoryData()
        }
    }

    override fun update() {
        updateHistoryData()
    }

    @SuppressLint("CheckResult")
    private fun getText() = retrofit.getText().observeOn(AndroidSchedulers.mainThread()).subscribe({ result ->
        setToCache(result.joke).subscribe {
            view?.setText(result.joke)
            updateHistoryData()
        }
    }, { showError(it) })

    private fun setToCache(str: String) =
        cache.insert(RoomMessage(0, str)).observeOn(AndroidSchedulers.mainThread())

    private fun clearCache() =
        cache.deleteAll().observeOn(AndroidSchedulers.mainThread())

    private fun updateHistoryData() =
        cache.getAll().observeOn(AndroidSchedulers.mainThread()).subscribe { cacheList ->
            val list = mutableListOf<String>()
            cacheList.forEach {
                list.add(it.joke)
            }
            view?.setHistory(list)
        }

    private fun showError(t: Throwable) {
        t.message?.let { view?.showToast(it) }
    }
}