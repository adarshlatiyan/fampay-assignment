package com.adarsh.dynamicui.data.model


import com.google.gson.annotations.SerializedName

class Icon(
    @SerializedName("image_type")
    var imageType: String,
    @SerializedName("image_url")
    var imageUrl: String,
    @SerializedName("aspect_ratio")
    var aspectRatio: Double
)