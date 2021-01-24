package com.adarsh.dynamicui.model

import com.google.gson.annotations.SerializedName

data class Entity(
    @SerializedName("text")
    val text: String,
    @SerializedName("color")
    val color: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("fontStyle")
    val fontStyle: FontStyle?
)

enum class FontStyle(value: String) {
    @SerializedName("underline")
    UNDERLINE("underline"),

    @SerializedName("italic")
    ITALIC("italic")
}
