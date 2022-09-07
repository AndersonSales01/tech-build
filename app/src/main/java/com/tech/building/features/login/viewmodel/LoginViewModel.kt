package com.tech.building.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.domain.usecase.login.LoginUseCase
import com.tech.building.domain.usecase.network.HasInternetConnectionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val hasInternetConnectionUseCase: HasInternetConnectionUseCase
) : ViewModel() {

    private val loginUiStateMutableLiveData: MutableLiveData<LoginUiState> = MutableLiveData()
    val loginUiStateLiveData: LiveData<LoginUiState> = loginUiStateMutableLiveData

    private val loginUiActionMutableLiveData: MutableLiveData<LoginUiAction> = MutableLiveData()
    val loginUiActionLiveData: LiveData<LoginUiAction> = loginUiActionMutableLiveData

    fun efetuateLogin(
        user: String,
        password: String
    ) {
        if (hasInternetConnectionUseCase.invoke()) {
            viewModelScope.launch {
                loginUseCase.invoke(user = user, password = password)
                    .flowOn(dispatcher)
                    .onStart { showLoading() }
                    .onCompletion { hiddenLoading() }
                    .catch { loginHandleError() }
                    .collect {
                        loginHandleSuccess(it)
                    }
            }
        } else {
            loginUiActionMutableLiveData.value = LoginUiAction.ShowNetWorkErrorPage
        }
    }

    private fun loginHandleSuccess(isSuccess: Boolean) {
        if (isSuccess) {
            loginUiActionMutableLiveData.value = LoginUiAction.NavigateToHome
        } else {
            loginUiStateMutableLiveData.value = LoginUiState(hasError = true)
        }
    }

    private fun loginHandleError() {
        loginUiStateMutableLiveData.value = LoginUiState(hasError = true)
    }

    private fun showLoading() {
        loginUiStateMutableLiveData.value = LoginUiState(isLoading = true)
    }

    private fun hiddenLoading() {
        loginUiStateMutableLiveData.value = LoginUiState(isLoading = false)
    }

}