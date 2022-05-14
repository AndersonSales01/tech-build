package com.tech.building.domain.di

import com.tech.building.domain.usecase.LoginUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        LoginUseCase(
            repository = get()
        )
    }
}