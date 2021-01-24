package com.adarsh.dynamicui.util

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.Spannable
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import com.adarsh.dynamicui.model.Alignment
import com.adarsh.dynamicui.model.FontStyle
import com.adarsh.dynamicui.model.FormattedText

fun TextView.setFormattedText(
    formattedText: FormattedText?,
    defaultTitle: String?,
    defaultColor: Int = Color.BLACK
) {
    text = formattedText?.getFormattedSpan(
        defaultColor,
        textSize,
        context
    ) ?: defaultTitle

    textAlignment = when (formattedText?.align) {
        Alignment.LEFT -> View.TEXT_ALIGNMENT_TEXT_START
        Alignment.RIGHT -> View.TEXT_ALIGNMENT_TEXT_END
        Alignment.CENTER -> View.TEXT_ALIGNMENT_CENTER
        null -> View.TEXT_ALIGNMENT_TEXT_START
    }
    movementMethod = LinkMovementMethod.getInstance()
}

private fun FormattedText.getFormattedSpan(
    defaultColor: Int,
    textSize: Float,
    context: Context
): Spannable? {
    if (entities.isNullOrEmpty()) return Spannable.Factory.getInstance().newSpannable(text)
    else {
        var text = text
        for (entity in entities) {
            text = text?.replaceFirst("{}", entity.text)
        }

        val span = Spannable.Factory.getInstance().newSpannable(text)

        for (entity in entities) {
            val paint = Paint().apply {
                var textColor = defaultColor
                try {
                    textColor = Color.parseColor(entity.color)
                } catch (e: Exception) {
                }
                setTextSize(textSize)
                color = textColor
                isUnderlineText = false
                typeface = Typeface.DEFAULT
                entity.fontStyle?.let {
                    when (it) {
                        FontStyle.UNDERLINE -> {
                            isUnderlineText = true
                        }
                        FontStyle.ITALIC -> {
                            typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
                        }
                    }
                }
            }

            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    if (!entity.url.isNullOrEmpty()) {
                        context.openUrl(entity.url)
                    }
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.set(paint)
                }
            }

            text?.let {
                span.setSpan(
                    clickableSpan,
                    text.indexOf(entity.text),
                    text.indexOf(entity.text) + entity.text.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return span
    }
}
