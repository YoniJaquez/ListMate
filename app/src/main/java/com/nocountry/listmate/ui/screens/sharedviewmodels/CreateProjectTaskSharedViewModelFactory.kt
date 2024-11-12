package com.nocountry.listmate.ui.screens.sharedviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nocountry.listmate.domain.ProjectRepository

class CreateProjectTaskSharedViewModelFactory(
    private val projectRepository: ProjectRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateProjectTaskSharedViewModel::class.java)) {
            return CreateProjectTaskSharedViewModel(projectRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
