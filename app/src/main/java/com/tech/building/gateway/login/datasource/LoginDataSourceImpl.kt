package com.tech.building.gateway.login.datasource

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.tech.building.gateway.MocksData
import com.tech.building.gateway.login.entity.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val USER_DATA = "user_data"

class LoginDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : LoginDataSource {

    override fun login(user: String, password: String): Flow<Boolean> {
        return flow {
            emit(checkUserValid(user = user, password = password))
        }
    }

    private fun checkUserValid(user: String, password: String): Boolean {
        val user = MocksData.mockListLogin().find { it.user == user && it.password == password }
        if (user != null) {
            saveUser(user)
            return true
        }
        return false
    }

    private fun saveUser(userDTO: UserDTO) {
        sharedPreferences.edit().putString(USER_DATA, toUserSession(userDTO)).apply()
    }

    private fun toUserSession(userDTO: UserDTO): String {
        return gson.toJson(userDTO)
    }
}