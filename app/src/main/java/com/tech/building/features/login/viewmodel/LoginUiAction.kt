package com.tech.building.features.login.viewmodel

sealed class LoginUiAction {
    object NavigateToHome : LoginUiAction()

    object ShowNetWorkErrorPage : LoginUiAction()
}