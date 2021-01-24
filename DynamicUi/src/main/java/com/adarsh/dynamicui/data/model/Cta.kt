package com.adarsh.dynamicui.data.model


import com.google.gson.annotations.SerializedName

class Cta(
    @SerializedName("text")
    var text: String,
    @SerializedName("bg_color")
    var bgColor: String,
    @SerializedName("text_color")
    var textColor: String,
    @SerializedName("url_choice")
    var urlChoice: String,
    @SerializedName("url")
    var url: String
)