package com.adarsh.dynamicui.model


import com.google.gson.annotations.SerializedName

data class Cta(
    @SerializedName("bg_color")
    val bgColor: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("text_color")
    val textColor: String?,
    @SerializedName("url")
    val url: String?,
//    @SerializedName("url_choice")
//    val urlChoice: String?
)