package com.adarsh.dynamicui.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.adarsh.dynamicui.R
import com.adarsh.dynamicui.model.BgImage
import com.adarsh.dynamicui.model.Icon
import com.adarsh.dynamicui.model.ImageType
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

private const val TAG = "ImageExtensions"

fun View.loadBackgroundImage(bgImage: BgImage?) {
    bgImage?.let {
        when (it.imageType) {
            ImageType.ASSET -> {
//                            TODO()
            }
            ImageType.EXT, null -> {
                Glide.with(this)
                    .load(it.imageUrl)
                    .centerCrop()
                    .into(object : CustomTarget<Drawable>() {
                        override fun onResourceReady(
                            resource: Drawable,
                            transition: Transition<in Drawable>?
                        ) {
                            background = resource
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {}
                    })
            }
        }
    }
}

fun ImageView.loadIcon(icon: Icon) {
    icon.let {
        when (it.imageType) {
            ImageType.ASSET -> {
//                            TODO()
            }
            ImageType.EXT, null -> {
                it.imageUrl?.let { url ->
                    Glide.with(this)
                        .load(url)
                        .placeholder(R.drawable.ic_landscape)
                        .into(this)
                }
            }
        }
    }

}