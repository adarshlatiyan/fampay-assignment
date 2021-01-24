package com.adarsh.dynamicui.model


import com.google.gson.annotations.SerializedName

data class FormattedText(
    @SerializedName("align")
    val align: Alignment?,
    @SerializedName("entities")
    val entities: List<Entity>?,
    @SerializedName("text")
    val text: String?
)

enum class Alignment(value: String) {
    @SerializedName("left")
    LEFT("left"),
    @SerializedName("right")
    RIGHT("right"),
    @SerializedName("center")
    CENTER("center"),
}
