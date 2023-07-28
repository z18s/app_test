package com.example.testapp.presenter

import com.example.testapp.view.IMainView

class MainPresenter : IMainPresenter {

    private var view: IMainView? = null

    override fun attachView(view: IMainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }
}