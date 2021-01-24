package com.adarsh.dynamicui.view.adapter.card

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.model.Card

abstract class CardAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {
    abstract fun submitList(list: List<Card>)

    val diffCallback = object : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(
            oldItem: Card,
            newItem: Card
        ): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(
            oldItem: Card,
            newItem: Card
        ): Boolean {
            return (oldItem == newItem)
        }
    }
}