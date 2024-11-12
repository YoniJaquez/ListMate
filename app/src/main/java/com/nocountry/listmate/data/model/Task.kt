package com.nocountry.listmate.data.model

data class Task(
    var id: String = "",
    val projectId: String = "",
    val taskName: String = "",
    val assignedTo: String = "",
    val assignedToId: String = "",
    val description: String = "",
    var status: Boolean = false,
)
