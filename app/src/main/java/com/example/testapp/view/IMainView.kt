package com.example.testapp.view

interface IMainView {
    fun setText(text: String)
    fun setHistory(list: List<String>)
    fun showToast(text: String)
}