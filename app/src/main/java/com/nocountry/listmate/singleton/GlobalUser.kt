package com.nocountry.listmate.singleton

import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nocountry.listmate.data.model.User

class GlobalUser {
    companion object{
        var uid: String = ""
        var name: String = ""
        var lastName: String = ""
        var email: String = ""
        var userKey: String = ""
        fun initialize(user: QueryDocumentSnapshot){
            uid = user.data["uid"].toString()
            name = user.data["name"].toString()
            lastName = user.data["lastName"].toString()
            email = user.data["email"].toString()
            userKey = user.id
        }
        fun getUserObject(): User{
            return User(uid = uid,
             name = name,
             lastName = lastName,
             email = email,
             projects = emptyList())
        }
    }
}