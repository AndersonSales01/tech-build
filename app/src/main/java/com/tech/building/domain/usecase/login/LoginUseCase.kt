package com.tech.building.domain.usecase.login

import com.tech.building.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow

class LoginUseCase(
    private val repository: LoginRepository
) {
    operator fun invoke(user: String, password: String): Flow<Boolean> =
        repository.login(user = user, password = password)
}