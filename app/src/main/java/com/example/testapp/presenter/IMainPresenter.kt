package com.example.testapp.presenter

import com.example.testapp.view.IMainView

interface IMainPresenter {
    fun attachView(view: IMainView)
    fun detachView()
    fun onClick()
}