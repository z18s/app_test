package com.example.testapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.application.App
import com.example.testapp.model.retrofit.RetrofitConnection
import com.example.testapp.model.room.Cache
import com.example.testapp.presenter.MainPresenter
import com.example.testapp.view.adapter.RvAdapter

class MainActivity : AppCompatActivity(), IMainView {

    companion object {
        private const val MAIN_TAG = "main_text"
    }

    private lateinit var presenter: MainPresenter

    private lateinit var buttonQuery: Button
    private lateinit var buttonDelete: Button
    private lateinit var textView: TextView
    private lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        presenter = MainPresenter(RetrofitConnection(App.getInstance().dataSource), Cache(App.getInstance().database))
        presenter.attachView(this)

        buttonQuery = findViewById(R.id.button_main)
        buttonDelete = findViewById(R.id.button_delete_main)
        textView = findViewById(R.id.text_main)

        buttonQuery.setOnClickListener { presenter.onQueryClick() }
        buttonDelete.setOnClickListener { presenter.onDeleteClick() }

        rv = findViewById(R.id.rv_main)
        rv.layoutManager = LinearLayoutManager(baseContext)
        rv.adapter = RvAdapter(presenter.rvPresenter)

        presenter.initRvData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(MAIN_TAG, textView.text.toString())
        super.onSaveInstanceState(outState)
    }

    override fun setText(text: String) {
        textView.text = text
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateRv() {
        rv.adapter?.notifyDataSetChanged()
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