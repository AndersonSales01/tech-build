package com.tech.building.features.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.tech.building.R
import com.tech.building.features.home.HomeActivity
import com.tech.building.features.login.viewmodel.LoginUiAction
import com.tech.building.features.login.viewmodel.LoginViewModel
import com.tech.building.features.utils.provider.NetWorkErrorPage
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupListener()
        setStateObserver()
        setActionObserver()
    }

    private fun setupListener() {
        loginButton.setOnClickListener {
            viewModel.efetuateLogin(
                user = edtUser.text.toString(),
                password = edtPassword.text.toString()
            )
        }
    }

    private fun setStateObserver() {
        viewModel.loginUiStateLiveData.observe(this) { state ->
            when {
                state.isLoading -> {
                    showLoading(true)
                }
                state.hasError -> {
                    hiddenLoading(false)
                    toastError()
                }
                else -> {
                    hiddenLoading(false)
                }
            }
        }
    }

    private fun setActionObserver() {
        viewModel.loginUiActionLiveData.observe(this) { action ->
            when (action) {
                is LoginUiAction.NavigateToHome -> navigateToHome()
                is LoginUiAction.ShowNetWorkErrorPage -> showNetWorkErrorPage()
            }
        }
    }

    private fun showLoading(visible: Boolean) {
        loading.isVisible = visible
    }

    private fun hiddenLoading(visible: Boolean) {
        loading.isVisible = visible
    }

    private fun toastError() {
        Snackbar.make(root, R.string.login_screen_sing_in_error, Snackbar.LENGTH_LONG)
            .show()
    }

    private fun navigateToHome() {
        startActivity(HomeActivity.newIntent(this))
        finish()
    }

    private fun showNetWorkErrorPage() {
        val args = NetWorkErrorPage.Args(
            onTryAgain = {
                viewModel.efetuateLogin(
                    user = edtUser.text.toString(),
                    password = edtPassword.text.toString()
                )
            }
        )
        NetWorkErrorPage(args).show(supportFragmentManager, this::class.java.name)
    }
}