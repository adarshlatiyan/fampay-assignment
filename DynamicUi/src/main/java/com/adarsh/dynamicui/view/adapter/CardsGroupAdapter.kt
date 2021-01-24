package com.adarsh.dynamicui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adarsh.dynamicui.databinding.*
import com.adarsh.dynamicui.model.Card
import com.adarsh.dynamicui.model.CardGroup
import com.adarsh.dynamicui.model.DesignTypes
import com.adarsh.dynamicui.util.PrefsHelper
import com.adarsh.dynamicui.util.setCardData
import com.adarsh.dynamicui.view.adapter.card.*

class CardsGroupAdapter : RecyclerView.Adapter<CardsGroupAdapter.CardGroupViewHolder>() {
    var interactionListener: InteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardGroupViewHolder {
        return when (viewType) {
            SCROLLABLE_TYPE -> {
                val binding =
                    CardGroupItemScrollableBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ScrollableCardGroupViewHolder(binding, interactionListener)
            }

            else -> {
                val binding =
                    CardGroupItemNonScrollableBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                NonScrollableCardGroupViewHolder(binding, interactionListener)
            }
        }
    }

    override fun onBindViewHolder(holder: CardGroupViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun getItemViewType(position: Int): Int =
        when (differ.currentList[position].isScrollable) {
            false -> NON_SCROLLABLE_TYPE
            else -> SCROLLABLE_TYPE
        }


    fun submitList(list: List<CardGroup>) {
        differ.submitList(list.map { it.copy() })
    }

    private val diffCallback = object : DiffUtil.ItemCallback<CardGroup>() {
        override fun areItemsTheSame(
            oldItem: CardGroup,
            newItem: CardGroup
        ): Boolean {
            return (oldItem == newItem)
        }

        override fun areContentsTheSame(
            oldItem: CardGroup,
            newItem: CardGroup
        ): Boolean {
            return (oldItem.cards.containsAll(newItem.cards))
        }
    }
    private val differ = AsyncListDiffer(this, diffCallback)

    abstract class CardGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(cardGroup: CardGroup)
    }

    class ScrollableCardGroupViewHolder(
        private val binding: CardGroupItemScrollableBinding,
        private val listener: InteractionListener?
    ) : CardGroupViewHolder(binding.root) {
        override fun bind(cardGroup: CardGroup) {
            val adapter = when (cardGroup.designType) {
                DesignTypes.SMALL_DISPLAY_CARD -> SmallDisplayCardAdapter()
                DesignTypes.BIG_DISPLAY_CARD -> BigDisplayCardAdapter().apply {
                    remindLaterFun = { listener?.onRemindLater(adapterPosition, it) }
                    dismissFun = { listener?.onDismissCard(adapterPosition, it) }

                }
                DesignTypes.IMAGE_CARD -> ImageCardAdapter()
                DesignTypes.SMALL_CARD_WITH_ARROW -> SmallWithArrowCardAdapter()
                DesignTypes.DYNAMIC_WIDTH_CARD -> DynamicWidthCardAdapter(cardGroup.height)
            }

            with(binding) {
                cardGroupRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
                    this.adapter = adapter
                }
            }

            adapter.submitList(cardGroup.cards.filter { it.name != PrefsHelper(binding.root.context).getDismissedCard() })
        }
    }

    class NonScrollableCardGroupViewHolder(
        private val binding: CardGroupItemNonScrollableBinding,
        private val listener: InteractionListener?
    ) : CardGroupViewHolder(binding.root) {
        override fun bind(cardGroup: CardGroup) {
            val inflater = LayoutInflater.from(binding.root.context)
            val views = mutableListOf<View>()
            with(binding) {
                for (card in cardGroup.cards) {
                    if (card.name == PrefsHelper(binding.root.context).getDismissedCard()) continue
                    val cardBinding = when (cardGroup.designType) {
                        DesignTypes.SMALL_DISPLAY_CARD -> CardItemSmallDisplayBinding
                            .inflate(inflater, linearLayout, false)
                            .also {
                                it.setCardData(card)
                                it.tvTitle.maxLines = 1
                                it.tvDesc.maxLines = 1
                            }
                        DesignTypes.BIG_DISPLAY_CARD -> CardItemBigDisplayBinding
                            .inflate(inflater, linearLayout, false)
                            .also {
                                it.setCardData(
                                    card,
                                    this@NonScrollableCardGroupViewHolder::remindLater,
                                    this@NonScrollableCardGroupViewHolder::dismissCard
                                )
                            }
                        DesignTypes.IMAGE_CARD -> CardItemImageBinding
                            .inflate(inflater, linearLayout, false)
                            .also { it.setCardData(card) }
                        DesignTypes.SMALL_CARD_WITH_ARROW -> CardItemSmallWithArrowBinding
                            .inflate(inflater, linearLayout, false)
                            .also {
                                it.setCardData(card)
                                it.tvTitle.maxLines = 1
                                it.tvDesc.maxLines = 1
                            }
                        DesignTypes.DYNAMIC_WIDTH_CARD -> CardItemDynamicWidthBinding
                            .inflate(inflater, linearLayout, false)
                            .also { it.setCardData(card, cardGroup.height) }
                    }

                    val view = cardBinding.root.apply {
                        (layoutParams as LinearLayout.LayoutParams).weight = 1f
                        layoutParams.width = 0
                    }
                    views.add(view)
                }
                linearLayout.removeAllViews()
                for (v in views) {
                    linearLayout.addView(v)
                }
            }
        }

        private fun remindLater(card: Card) {
            listener?.onRemindLater(adapterPosition, card)
        }

        private fun dismissCard(card: Card) {
            listener?.onDismissCard(adapterPosition, card)
        }
    }


    interface InteractionListener {
        fun onRemindLater(position: Int, card: Card)
        fun onDismissCard(position: Int, card: Card)
    }

    companion object {
        private const val SCROLLABLE_TYPE = 0
        private const val NON_SCROLLABLE_TYPE = 1
    }
}
