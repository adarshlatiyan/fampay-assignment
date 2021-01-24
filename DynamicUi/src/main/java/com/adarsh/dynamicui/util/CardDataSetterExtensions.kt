package com.adarsh.dynamicui.util

import android.animation.AnimatorSet
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.adarsh.dynamicui.R
import com.adarsh.dynamicui.databinding.*
import com.adarsh.dynamicui.model.Card

fun CardItemBigDisplayBinding.setCardData(
    card: Card,
    remindFun: (card: Card) -> Unit,
    dismissFun: (card: Card) -> Unit
) {
    fun hideMenu(): Boolean {
        val menuHideAnim = android.animation.ValueAnimator.ofFloat(1f, 0f).apply {
            duration = 300
            addUpdateListener {
                val animatedValue = it.animatedValue as Float
                menuLayout.scaleX = animatedValue
                menuLayout.scaleY = animatedValue
            }
        }
        val slideAnim = android.animation.ValueAnimator.ofFloat(cardView.width / 3f, 0f).apply {
            duration = 300
            addUpdateListener {
                val animatedValue = it.animatedValue as Float
                cardView.translationX = animatedValue
            }
        }

        AnimatorSet().apply {
            playTogether(slideAnim, menuHideAnim)
            doOnEnd { menuLayout.visibility = View.GONE }
        }.start()
        return true
    }

    fun showMenu(): Boolean {
        val menuHideAnim = android.animation.ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 300
            addUpdateListener {
                val animatedValue = it.animatedValue as Float
                menuLayout.scaleX = animatedValue
                menuLayout.scaleY = animatedValue
            }
        }
        val slideAnim = android.animation.ValueAnimator.ofFloat(0f, cardView.width / 3f).apply {
            duration = 300
            addUpdateListener {
                val animatedValue = it.animatedValue as Float
                cardView.translationX = animatedValue
            }
        }

        AnimatorSet().apply {
            playTogether(slideAnim, menuHideAnim)
            doOnStart { menuLayout.visibility = View.VISIBLE }
        }.start()
        return true
    }

    tvTitle.setFormattedText(card.formattedTitle, card.title)
    tvDesc.setFormattedText(card.formattedDescription, card.description)
    ivBackground.loadBackgroundImage(card.bgImage)
    card.icon?.let { ivIcon.loadIcon(it) }
    card.icon?.aspectRatio?.let { ivIcon.setAspectRatio(it) }

    card.bgImage?.aspectRatio?.let { ratio -> ivBackground.setAspectRatio(ratio) }
    card.bgColor?.let { cardView.setCardBackgroundColor(Color.parseColor(it)) }
    card.bgGradient?.let {
        val colors = arrayOf(
            Color.parseColor(it.colors?.get(0)),
            Color.parseColor(it.colors?.get(0)),
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors.toIntArray()
        ).apply { cornerRadius = it.angle?.toFloat() ?: 0f }
        cardView.background = gradientDrawable
    }


    card.ctaList?.let { ctaList ->
        for (cta in ctaList) {
            val action = Button(root.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )

                isAllCaps = false

                setPadding(
                    context.dpToPixel(36),
                    context.dpToPixel(4),
                    context.dpToPixel(36),
                    context.dpToPixel(4)
                )
                val drawable = ContextCompat.getDrawable(
                    context,
                    R.drawable.rounded_button_background
                )
                drawable?.let {
                    cta.bgColor?.let { color ->
                        DrawableCompat.setTint(
                            it,
                            Color.parseColor(color)
                        )
                    }
                }
                background = drawable

                text = cta.text
                setTextColor(Color.parseColor(cta.textColor))
                setOnClickListener { context.openUrl(cta.url) }
            }

            ctaLayout.addView(action)
        }
    }

    cardView.setOnClickListener {
        if (menuLayout.visibility == View.VISIBLE) hideMenu()
        else it.context.openUrl(card.url)
    }

    cardView.setOnLongClickListener {
        if (menuLayout.visibility == View.VISIBLE) hideMenu()
        else showMenu()
    }
    menuRemindLayout.setOnClickListener { remindFun(card) }
    menuDismissLayout.setOnClickListener { dismissFun(card) }

    cardView.isEnabled = !(card.isDisabled ?: false)
    cardView.isClickable = !(card.isDisabled ?: false)
}

fun CardItemDynamicWidthBinding.setCardData(card: Card, height: Int?) {
    card.bgColor?.let { cardView.setCardBackgroundColor(Color.parseColor(it)) }
    height?.let {
        ivBackground.layoutParams.height = ivBackground.context.dpToPixel(it)
    }
    ivBackground.loadBackgroundImage(card.bgImage)
    card.bgImage?.aspectRatio?.let { ratio -> ivBackground.setAspectRatio(ratio) }

    card.bgGradient?.let {
        val colors = arrayOf(
            Color.parseColor(it.colors?.get(0)),
            Color.parseColor(it.colors?.get(0)),
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors.toIntArray()
        ).apply { cornerRadius = it.angle?.toFloat() ?: 0f }
        cardView.background = gradientDrawable
    }


    cardView.isEnabled = !(card.isDisabled ?: false)
    cardView.isClickable = !(card.isDisabled ?: false)

    cardView.setOnClickListener { it.context.openUrl(card.url) }
}

fun CardItemImageBinding.setCardData(card: Card) {
    card.bgColor?.let { cardView.setCardBackgroundColor(Color.parseColor(it)) }
    ivBackground.loadBackgroundImage(card.bgImage)
    card.bgImage?.aspectRatio?.let { ratio -> ivBackground.setAspectRatio(ratio) }

    card.bgGradient?.let {
        val colors = arrayOf(
            Color.parseColor(it.colors?.get(0)),
            Color.parseColor(it.colors?.get(0)),
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors.toIntArray()
        ).apply { cornerRadius = it.angle?.toFloat() ?: 0f }
        cardView.background = gradientDrawable
    }


    cardView.isEnabled = !(card.isDisabled ?: false)
    cardView.isClickable = !(card.isDisabled ?: false)
    cardView.setOnClickListener { it.context.openUrl(card.url) }
}

fun CardItemSmallDisplayBinding.setCardData(card: Card) {
    cardView.setOnClickListener { it.context.openUrl(card.url) }

    tvTitle.setFormattedText(card.formattedTitle, card.title)
    tvDesc.setFormattedText(card.formattedDescription, card.description)
    card.bgImage?.imageUrl?.let { cardView.loadBackgroundImage(card.bgImage) }
    card.bgColor?.let { cardView.setCardBackgroundColor(Color.parseColor(it)) }

    card.bgGradient?.let {
        val colors = arrayOf(
            Color.parseColor(it.colors?.get(0)),
            Color.parseColor(it.colors?.get(0)),
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors.toIntArray()
        ).apply { cornerRadius = it.angle?.toFloat() ?: 0f }
        cardView.background = gradientDrawable
    }


    card.icon?.let { ivIcon.loadIcon(it) }
    card.icon?.aspectRatio?.let { ivIcon.setAspectRatio(it) }

    cardView.isEnabled = !(card.isDisabled ?: false)
    cardView.isClickable = !(card.isDisabled ?: false)

    cardView.setOnClickListener { it.context.openUrl(card.url) }
}

fun CardItemSmallWithArrowBinding.setCardData(card: Card) {
    cardView.setOnClickListener { it.context.openUrl(card.url) }

    tvTitle.setFormattedText(card.formattedTitle, card.title)
    tvDesc.setFormattedText(card.formattedDescription, card.description)
    card.bgImage?.imageUrl?.let { cardView.loadBackgroundImage(card.bgImage) }
    card.bgColor?.let { cardView.setCardBackgroundColor(Color.parseColor(it)) }

    card.bgGradient?.let {
        val colors = arrayOf(
            Color.parseColor(it.colors?.get(0)),
            Color.parseColor(it.colors?.get(0)),
        )
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            colors.toIntArray()
        ).apply { cornerRadius = it.angle?.toFloat() ?: 0f }
        cardView.background = gradientDrawable
    }


    card.icon?.let { ivIcon.loadIcon(it) }
    card.icon?.aspectRatio?.let { ivIcon.setAspectRatio(it) }

    cardView.isEnabled = !(card.isDisabled ?: false)
    cardView.isClickable = !(card.isDisabled ?: false)

    cardView.setOnClickListener { it.context.openUrl(card.url) }
}