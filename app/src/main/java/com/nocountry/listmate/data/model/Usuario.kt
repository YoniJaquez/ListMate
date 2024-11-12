package com.nocountry.listmate.data.model

data class Usuario (
    val uid: String = "",
    val nombre: String = "",
    val apellido: String = "",
    val correo: String = "",
    val projects: List<String> = emptyList()
)