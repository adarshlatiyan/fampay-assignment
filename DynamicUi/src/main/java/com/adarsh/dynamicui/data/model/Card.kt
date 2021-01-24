package com.adarsh.dynamicui.data.model


import com.google.gson.annotations.SerializedName

class Card(
    @SerializedName("name")
    var name: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("formatted_title")
    var formattedTitle: FormattedTitle,
    @SerializedName("description")
    var description: String,
    @SerializedName("formatted_description")
    var formattedDescription: FormattedDescription,
    @SerializedName("icon")
    var icon: Icon,
    @SerializedName("url")
    var url: String,
    @SerializedName("is_disabled")
    var isDisabled: Boolean,
    @SerializedName("bg_image")
    var bgImage: BgImage,
    @SerializedName("bg_color")
    var bgColor: String,
    @SerializedName("cta")
    var cta: List<Cta>
)