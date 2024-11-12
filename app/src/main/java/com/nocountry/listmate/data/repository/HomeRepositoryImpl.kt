package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.Project
import com.nocountry.listmate.domain.HomeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class HomeRepositoryImpl(private val firebase: FirebaseFirestore) : HomeRepository {
    override suspend fun getProjectsById(userId: String): Flow<List<Project>> = callbackFlow {
        var projects: MutableList<Project>

        firebase.collection("projects")
            .whereEqualTo("ownerId", userId)
            .get()
            .addOnSuccessListener { result ->
                projects = result.documents.mapNotNull { it.toObject(Project::class.java) }.toMutableList()
                trySend(projects)
            }
            .addOnFailureListener { exception ->
                Log.d("HomeRepositoryImpl", "Error getting projects: ", exception)
                trySend(emptyList())
            }
        awaitClose()
    }
}