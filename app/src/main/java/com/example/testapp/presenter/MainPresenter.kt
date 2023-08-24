package com.example.testapp.presenter

import com.example.testapp.model.entity.RoomMessage
import com.example.testapp.model.retrofit.IRetrofit
import com.example.testapp.model.room.ICache
import com.example.testapp.presenter.rv.RvPresenter
import com.example.testapp.view.IMainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MainPresenter(private var retrofit: IRetrofit, private val cache: ICache) : IMainPresenter {

    private var view: IMainView? = null
    override val rvPresenter = RvPresenter()

    override fun attachView(view: IMainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun initRvData() {
        updateRvData()
    }

    override fun onQueryClick() {
        getText()
    }

    override fun onDeleteClick() {
        clearCache()
    }

    private fun getText() = retrofit.getText().observeOn(AndroidSchedulers.mainThread()).subscribe({
        view?.apply {
            setText(it.joke)
            setToCache(it.joke)
        }
    }, { showError(it) })

    private fun setToCache(str: String) =
        cache.insert(RoomMessage(0, str)).observeOn(AndroidSchedulers.mainThread()).subscribe {
            updateRvData()
        }

    private fun clearCache() =
        cache.deleteAll().observeOn(AndroidSchedulers.mainThread()).subscribe {
            updateRvData()
        }

    private fun updateRvData() =
        cache.getAll().observeOn(AndroidSchedulers.mainThread()).subscribe({ cacheList ->
            val list = mutableListOf<String>()
            cacheList.forEach {
                list.add(it.joke)
            }
            rvPresenter.updateData(list)
            view?.updateRv()
        }, { showError(it) })

    private fun showError(t: Throwable) {
        t.message?.let { view?.showToast(it) }
    }
}