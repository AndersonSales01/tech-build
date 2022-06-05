package com.tech.building.gateway.material.datasource

import com.tech.building.domain.model.MaterialModel
import kotlinx.coroutines.flow.Flow

interface MaterialDataSource {
    fun getMaterials(): Flow<List<MaterialModel>>
}