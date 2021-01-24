package com.adarsh.dynamicui.data

import com.adarsh.dynamicui.model.UiResponse
import retrofit2.Response
import retrofit2.http.GET

interface UiService {
    @GET("fefcfbeb-5c12-4722-94ad-b8f92caad1ad")
    suspend fun getUiResponse(): Response<UiResponse>
}