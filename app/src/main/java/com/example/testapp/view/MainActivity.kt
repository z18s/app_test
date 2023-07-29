package com.example.testapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.testapp.R
import com.example.testapp.application.App
import com.example.testapp.model.retrofit.RetrofitConnection
import com.example.testapp.presenter.IMainPresenter
import com.example.testapp.presenter.MainPresenter

class MainActivity : AppCompatActivity(), IMainView {

    companion object {
        private const val MAIN_TAG = "main_text"
    }

    private lateinit var presenter: IMainPresenter

    private lateinit var button: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        presenter = MainPresenter(RetrofitConnection(App.INSTANCE.dataSource))
        presenter.attachView(this)

        button = findViewById(R.id.button_main)
        textView = findViewById(R.id.text_main)

        button.setOnClickListener { presenter.onClick() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MAIN_TAG, textView.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun setText(text: String) {
        textView.text = text
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        setText(savedInstanceState.getString(MAIN_TAG) ?: "")
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}