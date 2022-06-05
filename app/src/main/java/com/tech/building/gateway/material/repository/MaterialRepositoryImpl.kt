package com.tech.building.gateway.material.repository

import com.tech.building.domain.model.MaterialModel
import com.tech.building.domain.repository.MaterialRepository
import com.tech.building.gateway.material.datasource.MaterialDataSource
import kotlinx.coroutines.flow.Flow

class MaterialRepositoryImpl(
    private val dataSource: MaterialDataSource
) : MaterialRepository {
    override fun getMaterials(): Flow<List<MaterialModel>> =
        dataSource.getMaterials()
}