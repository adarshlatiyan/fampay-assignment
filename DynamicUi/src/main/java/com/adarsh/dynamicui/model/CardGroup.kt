package com.adarsh.dynamicui.model


import com.google.gson.annotations.SerializedName

data class CardGroup(
    @SerializedName("name")
    val name: String,
    @SerializedName("cards")
    val cards: MutableList<Card>,
    @SerializedName("design_type")
    val designType: DesignTypes,
    @SerializedName("height")
    val height: Int,
    @SerializedName("is_scrollable")
    val isScrollable: Boolean?,
//    @SerializedName("id")
//    val id: Int?,
//    @SerializedName("level")
//    val level: Int?,
//    @SerializedName("card_type")
//    val cardType: Int?,
) {

}

enum class DesignTypes(type: String) {
    @SerializedName("HC1")
    SMALL_DISPLAY_CARD("HC1"),

    @SerializedName("HC3")
    BIG_DISPLAY_CARD("HC3"),

    @SerializedName("HC5")
    IMAGE_CARD("HC5"),

    @SerializedName("HC6")
    SMALL_CARD_WITH_ARROW("HC6"),

    @SerializedName("HC9")
    DYNAMIC_WIDTH_CARD("HC9")
}