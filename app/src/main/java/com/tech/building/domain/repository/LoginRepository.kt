package com.tech.building.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    fun login(user: String, password: String): Flow<Boolean>
}