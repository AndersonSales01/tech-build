package com.tech.building.gateway.di

import android.content.Context
import com.google.gson.Gson
import com.tech.building.domain.repository.LoginRepository
import com.tech.building.gateway.login.datasource.LoginDataSource
import com.tech.building.gateway.login.datasource.LoginDataSourceImpl
import com.tech.building.gateway.login.repository.LoginRepositoryImpl
import org.koin.dsl.module

const val USER_DATA = "user_session"
val gatewayModule = module {
    // Repositories
    factory<LoginRepository> {
        LoginRepositoryImpl(
            loginDataSource = get()
        )
    }

    // DataSources
    factory<LoginDataSource> {
        LoginDataSourceImpl(
            sharedPreferences = get<Context>().getSharedPreferences(
                USER_DATA,
                Context.MODE_PRIVATE
            ),
            gson = Gson()
        )
    }
}