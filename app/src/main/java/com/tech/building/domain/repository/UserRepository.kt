package com.tech.building.domain.repository

import com.tech.building.domain.model.User

interface UserRepository {
    fun getUserData(): User?
}