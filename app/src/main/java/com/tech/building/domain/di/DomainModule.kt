package com.tech.building.domain.di

import com.tech.building.domain.usecase.cardcarousel.GetCardsCarouselHasPermissionUseCase
import com.tech.building.domain.usecase.login.LoginUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        LoginUseCase(
            repository = get()
        )
    }
    factory {
        GetCardsCarouselHasPermissionUseCase(
            repository = get()
        )
    }
}