package com.example.testapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.databinding.ItemRvBinding
import com.example.testapp.presenter.rv.IRvPresenter

class RvAdapter(private val presenter: IRvPresenter): RecyclerView.Adapter<RvAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = presenter.getCount()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener { presenter.onItemClick(holder) }
        presenter.bindView(holder)
    }

    class ViewHolder(private val binding: ItemRvBinding) : RecyclerView.ViewHolder(binding.root), IRvItemView {

        override fun setText(text: String) {
            binding.textRv.text = text
        }

        override fun getPos(): Int = adapterPosition
    }
}