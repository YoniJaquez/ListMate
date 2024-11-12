package com.nocountry.listmate.ui.screens.sharedviewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

    private val _userId = MutableStateFlow("")
    val userId: StateFlow<String> = _userId.asStateFlow()

    fun setUserId(userId: String) {
        _userId.value = userId
        savedStateHandle["userId"] = userId
    }
}