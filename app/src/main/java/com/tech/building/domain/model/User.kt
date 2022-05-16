package com.tech.building.domain.model

data class User(
    val user: String,
    val password: String,
    val name: String,
    val email: String,
    val userProfile: String,
    val phone: String,
    val permissions: List<String>
)

enum class UserProfile(){
    ADMIN,
    ENGINEER,
    WAREHOUSE
}