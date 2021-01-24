package com.adarsh.dynamicui.model


import com.google.gson.annotations.SerializedName

data class UiResponse(
    @SerializedName("card_groups")
    val cardGroups: MutableList<CardGroup>
)