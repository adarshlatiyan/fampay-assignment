package com.adarsh.dynamicui.view.adapter.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.databinding.CardItemSmallDisplayBinding
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.util.setCardData

class SmallDisplayCardAdapter : CardAdapter<SmallDisplayCardAdapter.SmallDisplayCardViewHolder>() {
    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallDisplayCardViewHolder {
        val binding = CardItemSmallDisplayBinding.inflate(LayoutInflater.from(parent.context))
        return SmallDisplayCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmallDisplayCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun submitList(list: List<Card>) {
        differ.submitList(list.map { it.copy() })
    }

    class SmallDisplayCardViewHolder(private val binding: CardItemSmallDisplayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.setCardData(card)
        }
    }
}