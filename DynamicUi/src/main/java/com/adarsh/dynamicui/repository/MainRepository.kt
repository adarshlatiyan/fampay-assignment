package com.adarsh.dynamicui.repository

import android.util.Log
import com.adarsh.dynamicui.data.RetrofitProvider
import com.adarsh.dynamicui.util.DataState
import kotlinx.coroutines.flow.flow
import org.json.JSONObject

class MainRepository {
    private val service = RetrofitProvider.uiService

    @Suppress(
        "BlockingMethodInNonBlockingContext",
        "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
    )
    fun getUiResponse() = flow {
        emit(DataState.Loading)
        try {
            val response = service.getUiResponse()

            if (response.isSuccessful && response.body() != null) emit(DataState.Success(response.body()!!))
            else {
                val errorBody = response.errorBody()?.string()
                val error = try {
                    JSONObject(errorBody).getString("error")
                } catch (e: Exception) {
                    null
                }
                if (error != null) emit(DataState.Failure(Exception(error)))
                else emit(DataState.Failure(Exception("Unknown error")))
            }
        } catch (e: Exception) {
            emit(DataState.Failure(e))
        }
    }
}