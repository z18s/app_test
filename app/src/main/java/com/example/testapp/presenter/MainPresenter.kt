package com.example.testapp.presenter

import com.example.testapp.model.retrofit.IRetrofit
import com.example.testapp.model.entity.Message
import com.example.testapp.view.IMainView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(private var retrofit: IRetrofit) : IMainPresenter {

    private var view: IMainView? = null

    override fun attachView(view: IMainView) {
        this.view = view
    }

    override fun detachView() {
        view = null
    }

    override fun onClick() = getText()

    private fun getText() = retrofit.getText().enqueue(object : Callback<Message> {

        override fun onResponse(call: Call<Message>, response: Response<Message>) {
            view?.apply {
                if (response.isSuccessful) {
                    setText(response.body()?.joke.toString())
                } else {
                    showToast(response.code().toString())
                }
            }
        }

        override fun onFailure(call: Call<Message>, t: Throwable) {
            view?.showToast(t.message ?: "Some error")
        }
    })
}