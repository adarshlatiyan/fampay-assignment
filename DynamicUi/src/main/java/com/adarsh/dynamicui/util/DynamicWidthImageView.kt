package com.adarsh.dynamicui.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView

class DynamicWidthImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    AppCompatImageView(context, attrs, defStyleAttr) {

    private var aspectRatio = 1.toDouble()

    fun setAspectRatio(ratio: Double) {
        aspectRatio = ratio
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension((measuredHeight * aspectRatio).toInt(), measuredHeight)
    }
}