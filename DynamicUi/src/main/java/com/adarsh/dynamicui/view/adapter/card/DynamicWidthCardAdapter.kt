package com.adarsh.dynamicui.view.adapter.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.databinding.CardItemDynamicWidthBinding
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.util.setCardData
import com.adarsh.dynamicui.view.adapter.CardsGroupAdapter

class DynamicWidthCardAdapter(private val height: Int?) :
    CardAdapter<DynamicWidthCardAdapter.DynamicWidthCardViewHolder>() {
    private val differ = AsyncListDiffer(this, diffCallback)

    var interactionListener: CardsGroupAdapter.InteractionListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DynamicWidthCardViewHolder {
        val binding = CardItemDynamicWidthBinding.inflate(LayoutInflater.from(parent.context))
        return DynamicWidthCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DynamicWidthCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position], height)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun submitList(list: List<Card>) {
        differ.submitList(list.map { it.copy() })
    }

    class DynamicWidthCardViewHolder(private val binding: CardItemDynamicWidthBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card, height: Int?) {
            binding.setCardData(card, height)
        }
    }
}