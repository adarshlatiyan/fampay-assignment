package com.adarsh.dynamicui.model


import com.google.gson.annotations.SerializedName

data class Icon(
    @SerializedName("aspect_ratio")
    val aspectRatio: Double?,
    @SerializedName("image_type")
    val imageType: ImageType?,
    @SerializedName("image_url")
    val imageUrl: String?
)

enum class ImageType(value: String) {
    @SerializedName("asset")
    ASSET("asset"),

    @SerializedName("ext")
    EXT("ext")
}