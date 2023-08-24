package com.example.testapp.presenter

import com.example.testapp.presenter.rv.RvPresenter
import com.example.testapp.view.IMainView

interface IMainPresenter {
    val rvPresenter: RvPresenter
    fun attachView(view: IMainView)
    fun detachView()
    fun initRvData()
    fun onQueryClick()
    fun onDeleteClick()
}