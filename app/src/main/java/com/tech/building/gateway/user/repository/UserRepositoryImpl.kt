package com.tech.building.gateway.user.repository

import com.tech.building.domain.model.User
import com.tech.building.domain.repository.UserRepository
import com.tech.building.gateway.user.datasource.UserDataSource

class UserRepositoryImpl(
    private val dataSource: UserDataSource
) : UserRepository {
    override fun getUserData(): User? {
        return dataSource.getUserDataLogged()
    }
}