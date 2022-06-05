package com.tech.building.domain.usecase.material

import com.tech.building.domain.model.MaterialModel
import com.tech.building.domain.repository.MaterialRepository
import kotlinx.coroutines.flow.Flow

class GetMaterialsUseCase(
    private val repository: MaterialRepository
) {
    operator fun invoke(): Flow<List<MaterialModel>> =
        repository.getMaterials()
}