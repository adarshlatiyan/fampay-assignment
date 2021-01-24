package com.adarsh.dynamicui.model


import com.google.gson.annotations.SerializedName

data class BgGradient(
    @SerializedName("colors")
    val colors: List<String>?,
    @SerializedName("angle")
    val angle: Int?,
    @SerializedName("image_url")
    val imageUrl: String?
)