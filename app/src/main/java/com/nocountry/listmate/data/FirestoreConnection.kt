package com.nocountry.listmate.data

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class FirestoreConnection {
    companion object{
        val usuarioDB = Firebase.firestore.collection("users")
    }
}