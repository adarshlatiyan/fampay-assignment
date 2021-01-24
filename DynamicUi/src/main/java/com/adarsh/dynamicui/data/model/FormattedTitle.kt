package com.adarsh.dynamicui.data.model


import com.google.gson.annotations.SerializedName

class FormattedTitle(
    @SerializedName("text")
    var text: String,
    @SerializedName("align")
    var align: String,
    @SerializedName("entities")
    var entities: List<Any>
)