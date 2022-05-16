package com.tech.building.gateway.user.datasource

import android.content.SharedPreferences
import com.tech.building.domain.model.User
import com.tech.building.gateway.user.entity.UserData
import com.tech.building.gateway.util.fromJson

private const val USER_DATA = "user_data"

class UserDataSourceImpl(
    private val sharedPreferences: SharedPreferences,
) : UserDataSource {
    override fun getUserDataLogged(): User? {
        val dataUser = getUserDataPersisted()
        return User(
            user = dataUser?.user ?: "",
            password = dataUser?.password ?: "",
            name = dataUser?.name ?: "",
            email = dataUser?.email ?: "",
            userProfile = dataUser?.userProfile ?: "",
            phone = dataUser?.phone ?: "",
            permissions = dataUser?.permissions ?: emptyList()
        )
    }

    private fun getUserDataPersisted(): UserData? {
        val data = sharedPreferences.getString(USER_DATA, "")
        return data?.toUserSession()
    }

    private fun String.toUserSession(): UserData {
        return fromJson()
    }
}