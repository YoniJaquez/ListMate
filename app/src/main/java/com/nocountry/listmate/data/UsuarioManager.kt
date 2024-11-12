package com.nocountry.listmate.data

import com.google.firebase.firestore.SetOptions
import com.nocountry.listmate.data.model.User

//referencia https://firebase.google.com/docs/firestore/quickstart?hl=es-419#android_1
class UsuarioManager {
    fun guardarUsuario(usuario: User) {
        FirestoreConnection.usuarioDB.add(usuario)
            .addOnSuccessListener { documentReference ->
            }
            .addOnFailureListener { e ->
            }
    }
    fun actualizarNombreApellidoUsuario(key: String, usuario: User) {
        val userUpdates = hashMapOf<String, Any>(
            "name" to usuario.name,
            "lastName" to usuario.lastName
        )

        FirestoreConnection.usuarioDB.document(key)
            .set(userUpdates, SetOptions.merge())
            .addOnSuccessListener {
                // Manejar el éxito de la actualización
            }
            .addOnFailureListener { e ->
                // Manejar el error
            }
    }
}