package com.adarsh.dynamicui.data.model


import com.google.gson.annotations.SerializedName

class CardGroup(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("design_type")
    var designType: String,
    @SerializedName("cards")
    var cards: List<Card>,
    @SerializedName("is_scrollable")
    var isScrollable: Boolean,
    @SerializedName("height")
    var height: Int,
    @SerializedName("card_type")
    var cardType: Int,
    @SerializedName("level")
    var level: Int
)