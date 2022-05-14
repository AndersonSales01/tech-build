package com.tech.building.gateway.login.datasource

import kotlinx.coroutines.flow.Flow

interface LoginDataSource {
    fun login(user: String, password: String): Flow<Boolean>
}