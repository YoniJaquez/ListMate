package com.nocountry.listmate.ui.screens.my_tasks

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nocountry.listmate.domain.MyTasksRepository

class MyTasksViewModelFactory(private val myTasksRepository: MyTasksRepository, userId: String) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    private val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyTasksViewModel::class.java)) {
            return MyTasksViewModel(myTasksRepository, savedStateHandle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}