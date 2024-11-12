package com.nocountry.listmate.data.model

data class Project(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val participants: List<String> = emptyList(),
    val tasks: List<String>? = emptyList(),
    val ownerId: String = ""

)
