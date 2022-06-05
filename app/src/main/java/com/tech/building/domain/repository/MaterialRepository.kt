package com.tech.building.domain.repository

import com.tech.building.domain.model.MaterialModel
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    fun getMaterials(): Flow<List<MaterialModel>>
}