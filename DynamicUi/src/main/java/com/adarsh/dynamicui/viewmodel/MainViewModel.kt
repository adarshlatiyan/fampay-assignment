package com.adarsh.dynamicui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.adarsh.dynamicui.model.UiResponse
import com.adarsh.dynamicui.repository.MainRepository
import com.adarsh.dynamicui.util.DataState
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class MainViewModel(private val app: Application) : AndroidViewModel(app) {
    private val repository = MainRepository()

    private val _dataState = MutableLiveData<DataState<UiResponse>>()
    val dataState get() = _dataState as LiveData<DataState<UiResponse>>

    fun setStateEvent(event: UiStateEvent) {
        viewModelScope.launch {
            when (event) {
                UiStateEvent.FetchEvent -> {
                    repository.getUiResponse().collect {
                        withContext(viewModelScope.coroutineContext) {
                            _dataState.value = it
                        }
                    }
                }
                UiStateEvent.None -> return@launch
                UiStateEvent.FetchLocal -> {
//                    TODO check this
                    if (!getJson().isNullOrEmpty()) withContext(viewModelScope.coroutineContext) {
                        _dataState.value = DataState.Loading
                        delay(1000)
                        val i = Random.nextInt(10)
                        Log.d("TAG", "setStateEvent: $i")
                        if (i % 4 == 0) _dataState.value = DataState.Failure(Exception(""))
                        else _dataState.value = DataState.Success(
                            GsonBuilder().create().fromJson(getJson(), UiResponse::class.java)
                        )
                    }
                }
            }
        }
    }

    private fun getJson(): String? {
        val inputStream = app.applicationContext.assets.open("json.txt")
        return inputStream.bufferedReader().use { it.readText() }
    }

    sealed class UiStateEvent {
        object FetchEvent : UiStateEvent()
        object FetchLocal : UiStateEvent()
        object None : UiStateEvent()
    }
}