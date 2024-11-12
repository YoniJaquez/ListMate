package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.domain.MyTasksRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MyTasksRepositoryImpl(private val firebase: FirebaseFirestore) : MyTasksRepository {

    override suspend fun getMyTasks(userId: String): Flow<List<Task>> = callbackFlow {

        firebase.collection("tasks")
            .whereEqualTo("assignedToId", userId)
            .get()
            .addOnSuccessListener { result ->
                val myTasks = result.documents.mapNotNull { document ->
                    val task = document.toObject(Task::class.java)
                    task?.let { it.id = document.id }
                    task
                }.toMutableList()
                trySend(myTasks)
            }
            .addOnFailureListener { exception ->
                Log.d("MyTasksRepositoryImpl", "Error getting my tasks: ", exception)
                trySend(emptyList())
            }
        awaitClose()
    }

    override suspend fun updateTaskStatus(taskId: String, status: Boolean) {
        firebase.collection("tasks").document(taskId)
            .update("status", status)
            .addOnSuccessListener {
                Log.d("MyTasksRepositoryImpl", "Task status updated successfully.")
            }
            .addOnFailureListener { exception ->
                Log.d("MyTasksRepositoryImpl", "Error updating task status: ", exception)
            }
    }

    override suspend fun getProjectName(projectId: String): String? {
        return try {
            val document = firebase.collection("projects").document(projectId).get().await()
            document.getString("name")
        } catch (e: Exception) {
            Log.d("MyTasksRepositoryImpl", "Error getting project name: ", e)
            null
        }
    }
}