package com.example.testapp.presenter.rv

import com.example.testapp.view.adapter.IRvItemView

interface IRvPresenter : IListPresenter<IRvItemView> {
    fun updateData(list: List<String>)
}