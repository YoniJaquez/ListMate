package com.nocountry.listmate.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.domain.UserRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class UserRepositoryImpl(private val firebase: FirebaseFirestore) : UserRepository {
    override fun getUserById(userId: String): Flow<User?> = callbackFlow {
        val query = firebase.collection("users").whereEqualTo("uid", userId)

        val listenerRegistration = query.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.d("UserRepositoryImpl", "Error getting user: ", error)
                trySend(null)
            } else {
                val user = snapshot?.documents?.firstOrNull()?.toObject(User::class.java)
                trySend(user)
            }
        }
        awaitClose { listenerRegistration.remove() }
    }
}