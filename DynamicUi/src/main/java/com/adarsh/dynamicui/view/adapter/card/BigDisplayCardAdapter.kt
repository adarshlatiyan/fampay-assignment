package com.adarsh.dynamicui.view.adapter.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.databinding.CardItemBigDisplayBinding
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.util.setCardData
import com.adarsh.dynamicui.view.adapter.CardsGroupAdapter

class BigDisplayCardAdapter() : CardAdapter<BigDisplayCardAdapter.BigDisplayCardViewHolder>() {
    companion object {
        private const val TAG = "BigDisplayCardAdapter"
    }

    var remindLaterFun : (card: Card) -> Unit = {}
    var dismissFun : (card: Card) -> Unit = {}

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BigDisplayCardViewHolder {
        val binding = CardItemBigDisplayBinding.inflate(LayoutInflater.from(parent.context))
        return BigDisplayCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BigDisplayCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun submitList(list: List<Card>) {
        differ.submitList(list.map { it.copy() })
    }

    inner class BigDisplayCardViewHolder(private val binding: CardItemBigDisplayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.apply {
                setCardData(card, remindLaterFun, dismissFun)
            }
        }
    }
}