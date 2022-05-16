package com.tech.building.features.di

import com.tech.building.features.cardcarousel.viewmodel.CardCarouselViewModel
import com.tech.building.features.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

    viewModel {
        CardCarouselViewModel(
            getCardsCarouselUseCase = get()
        )
    }
}