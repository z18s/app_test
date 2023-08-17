package com.example.testapp.presenter.rv

import com.example.testapp.view.adapter.IRvItemView

interface IListPresenter<in V: IRvItemView> {
    fun onItemClick(view: V)
    fun bindView(view: V)
    fun getCount(): Int
}