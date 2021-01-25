package com.adarsh.dynamicui.viewmodel

import androidx.lifecycle.*
import com.adarsh.dynamicui.model.UiResponse
import com.adarsh.dynamicui.repository.MainRepository
import com.adarsh.dynamicui.util.DataState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
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
            }
        }
    }

    sealed class UiStateEvent {
        object FetchEvent : UiStateEvent()
        object None : UiStateEvent()
    }
}