package com.nocountry.listmate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.data.model.Task
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.ProjectRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class ProjectRepositoryImpl(private val firebase: FirebaseFirestore) : ProjectRepository {
    override suspend fun createProject(
        projectName: String,
        ownerId: String,
        participants: List<String>?,
        tasks: List<String>?,
    ): Flow<Project> = callbackFlow {
        val project = hashMapOf(
            "name" to projectName,
            "ownerId" to ownerId,
            "participants" to participants,
            "tasks" to tasks
        )

        firebase.collection("projects")
            .add(project)
            .addOnSuccessListener { docRef ->
                val projectId = docRef.id
                val createProject =
                    participants?.let { Project(projectId, projectName, "", it, tasks, ownerId) }
                if (createProject != null) {
                    trySend(createProject).isSuccess
                }
            }
            .addOnFailureListener { close(it) }
        awaitClose()
    }

    override suspend fun createTasks(projectId: String, tasks: List<Task>): Flow<List<Task>> =
        callbackFlow {
            val updatedTasks = tasks.map { task ->
                val taskData = hashMapOf(
                    "projectId" to projectId,
                    "taskName" to task.taskName,
                    "assignedTo" to task.assignedTo,
                    "assignedToId" to task.assignedToId,
                    "description" to task.description,
                    "status" to task.status,
                )
                val docRef = firebase.collection("tasks").document()
                docRef.set(taskData).await()
                task.copy(id = docRef.id)
            }

            val taskIds = updatedTasks.map { it.id }
            firebase.collection("projects")
                .document(projectId)
                .update("tasks", taskIds)
                .await()

            trySend(updatedTasks).isSuccess
            awaitClose()
        }

    override suspend fun addParticipantsIds(
        projectId: String,
        participants: List<User>
    ): Flow<List<String>> =
        callbackFlow {
            val participantsIds = mutableListOf<String>()

            participants.forEach { participant ->
                val querySnapshot = firebase.collection("users")
                    .whereEqualTo("email", participant.email)
                    .get()
                    .await()

                if (!querySnapshot.isEmpty) {
                    val userDoc = querySnapshot.documents.first()
                    val userId = userDoc.getString("uid")
                    if (userId != null) {
                        participantsIds.add(userId)
                    }
                }
            }

            firebase.collection("projects")
                .document(projectId)
                .update("participants", participantsIds)
                .await()

            trySend(participantsIds)
            awaitClose()
        }

    override suspend fun fetchUsers(): Flow<List<User>> = callbackFlow {
        val usersCollection = firebase.collection("users")
        val snapshotListener = usersCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val usersList = snapshot.toObjects(User::class.java)
                trySend(usersList).isSuccess
            }
        }
        awaitClose { snapshotListener.remove() }
    }

}