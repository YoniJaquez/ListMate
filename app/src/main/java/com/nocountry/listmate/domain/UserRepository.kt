package com.nocountry.listmate.domain

import com.nocountry.listmate.data.model.User
import com.nocountry.listmate.data.model.Usuario
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserById(userId: String): Flow<User?>
}