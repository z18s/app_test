package com.example.testapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.presenter.rv.IRvPresenter

class RvAdapter(private val presenter: IRvPresenter): RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false))

    override fun getItemCount(): Int = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { presenter.onItemClick(holder) }
        presenter.bindView(holder)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), IRvItemView {

        private val tvRv: TextView = itemView.findViewById(R.id.text_rv)

        override fun setText(text: String) {
            tvRv.text = text
        }

        override fun getPos(): Int = adapterPosition
    }
}