package com.tech.building.gateway.material.entity

data class MaterialDTO(
    val name: String,
    val code: String,
    val unitMeasure: List<String>
)