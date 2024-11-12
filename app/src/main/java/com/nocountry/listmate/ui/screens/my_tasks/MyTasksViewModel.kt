package com.nocountry.listmate.ui.screens.my_tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.domain.MyTasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MyTasksViewModel(
    private val myTasksRepository: MyTasksRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _myTasks = MutableLiveData<List<Task>>(emptyList())
    val myTasks: LiveData<List<Task>> get() = _myTasks

    private val _projectNames = MutableLiveData<Map<String, String>>()
    val projectNames: LiveData<Map<String, String>> get() = _projectNames

    val userId = savedStateHandle.get<String>("userId")

    init {
        if (userId != null) {
            getMyTasks(userId)
        }
    }

    fun getMyTasks(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                myTasksRepository.getMyTasks(userId).collect { myTaskList ->
                    _myTasks.postValue(myTaskList.toMutableList())
                    fetchProjectNames(myTaskList.map { it.projectId }.distinct())
                }
            } catch (e: Exception) {
                Log.d("MyTaskViewModel", "Error getting myTasks from database ${e.message}")
            }
        }
    }

    private fun fetchProjectNames(projectIds: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            val projectNamesMap = mutableMapOf<String, String>()
            for (projectId in projectIds) {
                val projectName = myTasksRepository.getProjectName(projectId)
                projectName?.let {
                    projectNamesMap[projectId] = it
                }
            }
            _projectNames.postValue(projectNamesMap)
        }
    }


    fun updateTaskStatus(taskId: String, status: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            myTasksRepository.updateTaskStatus(taskId, status)

            _myTasks.value?.let { taskList ->
                val updatedTaskList = taskList.map { task ->
                    if (task.id == taskId) {
                        task.copy(status = status)
                    } else {
                        task
                    }
                }.toMutableList()

                _myTasks.postValue(updatedTaskList)
                getMyTasks(userId ?: return@launch)
            }
        }
    }
}
