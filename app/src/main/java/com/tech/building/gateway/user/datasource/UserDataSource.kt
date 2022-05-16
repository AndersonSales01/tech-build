package com.tech.building.gateway.user.datasource

import com.tech.building.domain.model.User
import com.tech.building.gateway.user.entity.UserData

interface UserDataSource {
    fun getUserDataLogged(): User?
}