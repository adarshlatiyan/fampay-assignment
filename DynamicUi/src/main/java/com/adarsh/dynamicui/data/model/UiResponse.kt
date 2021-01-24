package com.adarsh.dynamicui.data.model


import com.google.gson.annotations.SerializedName

class UiResponse(
    @SerializedName("card_groups")
    var cardGroups: List<CardGroup>
)