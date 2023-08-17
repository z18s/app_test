package com.example.testapp.presenter.rv

import com.example.testapp.view.adapter.IRvItemView

class RvPresenter : IRvPresenter {
    private var list = listOf<String>()

    override fun updateData(list: List<String>) {
        this.list = list
    }

    override fun onItemClick(view: IRvItemView) {}

    override fun bindView(view: IRvItemView) {
        val index: Int = view.getPos()
        val text = list[index]
        view.setText(text)
    }

    override fun getCount(): Int = list.size
}