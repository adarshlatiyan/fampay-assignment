package com.adarsh.dynamicui.view.adapter.card

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.databinding.CardItemSmallWithArrowBinding
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.util.*
import com.adarsh.dynamicui.view.adapter.CardsGroupAdapter

class SmallWithArrowCardAdapter :
    CardAdapter<SmallWithArrowCardAdapter.SmallWithArrowCardViewHolder>() {
    private val differ = AsyncListDiffer(this, diffCallback)

    var interactionListener: CardsGroupAdapter.InteractionListener? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SmallWithArrowCardViewHolder {
        val binding = CardItemSmallWithArrowBinding.inflate(LayoutInflater.from(parent.context))
        return SmallWithArrowCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SmallWithArrowCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun submitList(list: List<Card>) {
        differ.submitList(list.map { it.copy() })
    }

    class SmallWithArrowCardViewHolder(private val binding: CardItemSmallWithArrowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.setCardData(card)
        }
    }
}