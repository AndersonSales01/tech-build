package com.tech.building.gateway.user.entity

data class UserData(
    val user: String,
    val password: String,
    val name: String,
    val email: String,
    val userProfile: String,
    val phone: String,
    val permissions: List<String>
)