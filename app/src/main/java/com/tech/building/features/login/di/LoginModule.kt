package com.tech.building.features.login.di

import com.tech.building.features.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel {
        LoginViewModel()
    }
}