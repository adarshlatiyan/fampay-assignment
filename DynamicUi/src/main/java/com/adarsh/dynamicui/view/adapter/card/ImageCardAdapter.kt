package com.adarsh.dynamicui.view.adapter.card

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.databinding.CardItemImageBinding
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.util.loadBackgroundImage
import com.adarsh.dynamicui.util.openUrl
import com.adarsh.dynamicui.util.setCardData
import com.adarsh.dynamicui.view.adapter.CardsGroupAdapter

class ImageCardAdapter : CardAdapter<ImageCardAdapter.ImageCardViewHolder>() {
    private val differ = AsyncListDiffer(this, diffCallback)

    var interactionListener: CardsGroupAdapter.InteractionListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewHolder {
        val binding = CardItemImageBinding.inflate(LayoutInflater.from(parent.context))
        return ImageCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageCardViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun submitList(list: List<Card>) {
        differ.submitList(list.map { it.copy() })
    }

    class ImageCardViewHolder(private val binding: CardItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: Card) {
            binding.setCardData(card)
        }
    }
}