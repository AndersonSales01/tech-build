package com.tech.building.features.login.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.features.login.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity(R.layout.activity_login) {

    private val viewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}