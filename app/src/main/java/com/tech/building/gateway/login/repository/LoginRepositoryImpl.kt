package com.tech.building.gateway.login.repository

import com.tech.building.domain.repository.LoginRepository
import com.tech.building.gateway.login.datasource.LoginDataSource
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl(
    private val loginDataSource: LoginDataSource
) : LoginRepository {
    override fun login(user: String, password: String): Flow<Boolean> =
        loginDataSource.login(
            user = user,
            password = password
        )
}