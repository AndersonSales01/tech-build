package com.tech.building.gateway.di

import android.content.Context
import com.google.gson.Gson
import com.tech.building.domain.repository.CollaboratorRepository
import com.tech.building.domain.repository.LoginRepository
import com.tech.building.domain.repository.MaterialRepository
import com.tech.building.domain.repository.UserRepository
import com.tech.building.gateway.collaborator.datasource.CollaboratorDataSource
import com.tech.building.gateway.collaborator.datasource.CollaboratorDataSourceImpl
import com.tech.building.gateway.collaborator.mapper.ListCollaboratorDtoToListCollaboratorModelMapper
import com.tech.building.gateway.collaborator.repository.CollaboratorRepositoryImpl
import com.tech.building.gateway.login.datasource.LoginDataSource
import com.tech.building.gateway.login.datasource.LoginDataSourceImpl
import com.tech.building.gateway.login.repository.LoginRepositoryImpl
import com.tech.building.gateway.material.datasource.MaterialDataSource
import com.tech.building.gateway.material.datasource.MaterialDataSourceImpl
import com.tech.building.gateway.material.mapper.MaterialsDtoToMaterialsModelMapper
import com.tech.building.gateway.material.repository.MaterialRepositoryImpl
import com.tech.building.gateway.user.datasource.UserDataSource
import com.tech.building.gateway.user.datasource.UserDataSourceImpl
import com.tech.building.gateway.user.repository.UserRepositoryImpl
import org.koin.dsl.module

const val USER_DATA = "user_session"
val gatewayModule = module {
    // Repositories
    factory<LoginRepository> {
        LoginRepositoryImpl(
            loginDataSource = get()
        )
    }

    factory<UserRepository> {
        UserRepositoryImpl(
            dataSource = get()
        )
    }

    factory<CollaboratorRepository> {
        CollaboratorRepositoryImpl(
            dataSource = get()
        )
    }

    factory<MaterialRepository> {
        MaterialRepositoryImpl(
            dataSource = get()
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

    factory<UserDataSource> {
        UserDataSourceImpl(
            sharedPreferences = get<Context>().getSharedPreferences(
                USER_DATA,
                Context.MODE_PRIVATE
            )
        )
    }

    factory<CollaboratorDataSource> {
        CollaboratorDataSourceImpl(
            mapper = ListCollaboratorDtoToListCollaboratorModelMapper()
        )
    }

    factory<MaterialDataSource> {
        MaterialDataSourceImpl(
            mapper = MaterialsDtoToMaterialsModelMapper()
        )
    }
}