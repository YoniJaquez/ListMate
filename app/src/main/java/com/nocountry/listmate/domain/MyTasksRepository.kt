package com.nocountry.listmate.domain

import com.nocountry.listmate.data.model.Task
import kotlinx.coroutines.flow.Flow

interface MyTasksRepository {

    suspend fun getMyTasks(userId: String): Flow<List<Task>>

    suspend fun updateTaskStatus(taskId: String, status: Boolean)

    suspend fun getProjectName(projectId: String): String?
}