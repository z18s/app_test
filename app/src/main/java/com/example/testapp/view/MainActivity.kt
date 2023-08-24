package com.example.testapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.application.App
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.model.retrofit.RetrofitConnection
import com.example.testapp.model.room.Cache
import com.example.testapp.presenter.IMainPresenter
import com.example.testapp.presenter.MainPresenter
import com.example.testapp.view.adapter.RvAdapter

class MainActivity : AppCompatActivity(), IMainView {

    companion object {
        private const val MAIN_TAG = "main_text"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: IMainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        presenter = MainPresenter(RetrofitConnection(App.getInstance().dataSource), Cache(App.getInstance().database))
        presenter.attachView(this)

        binding.buttonMain.setOnClickListener { presenter.onQueryClick() }
        binding.buttonDeleteMain.setOnClickListener { presenter.onDeleteClick() }

        binding.rvMain.layoutManager = LinearLayoutManager(baseContext)
        binding.rvMain.adapter = RvAdapter(presenter.rvPresenter)

        presenter.initRvData()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val text = binding.textMain.text.toString()
        outState.putString(MAIN_TAG, text)
        super.onSaveInstanceState(outState)
    }

    override fun setText(text: String) {
        binding.textMain.text = text
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun updateRv() {
        binding.rvMain.adapter?.notifyDataSetChanged()
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