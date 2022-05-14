package com.tech.building.gateway.login.entity

data class UserDTO(
    val user: String,
    val password: String,
    val name: String,
    val email: String,
    val userProfile: String,
    val phone: String
)