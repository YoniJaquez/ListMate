package com.nocountry.listmate.ui.screens.home

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.data.model.Usuario
import com.nocountry.listmate.data.repository.HomeRepositoryImpl
import com.nocountry.listmate.data.repository.UserRepositoryImpl
import com.nocountry.listmate.domain.HomeRepository
import com.nocountry.listmate.domain.UserRepository
import com.nocountry.listmate.ui.navigation.Destinations
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val homeRepository: HomeRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
    val userId = savedStateHandle.get<String>("userId")


    init {

        if (userId != null) {
            getProjectsById(userId)
        }
    }

    private fun getProjectsById(userId: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                Log.d("HomeScreenViewModel", "Fetching project data for userId: $userId")
                homeRepository.getProjectsById(userId).collect { projects ->
                    _uiState.update { it.copy(isLoading = false, projects = projects) }

                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isError = "Error getting projects from database ${e.message}"
                    )
                }
            }
        }
    }

    suspend fun getUserById(userId: String): User? {
        return try {
            userRepository.getUserById(userId).firstOrNull()
        } catch (e: Exception) {
            Log.d("HomeScreenViewModel", "Error getting user: ", e)
            null
        }
    }


    companion object {
        fun provideFactory(userId: String): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val savedStateHandle = SavedStateHandle(mapOf("userId" to userId))
                return HomeScreenViewModel(
                    HomeRepositoryImpl(FirebaseFirestore.getInstance()),
                    UserRepositoryImpl(FirebaseFirestore.getInstance()),
                    savedStateHandle
                ) as T
            }
        }
    }


}