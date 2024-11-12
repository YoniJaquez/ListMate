package com.nocountry.listmate.ui.screens.home

import com.nocountry.listmate.data.model.Project

data class HomeUiState(
    val isLoading: Boolean = false,
    val projects : List<Project> = emptyList(),
    val isError: String = ""
)
