package com.tech.building.gateway.material.datasource

import com.tech.building.domain.model.MaterialModel
import com.tech.building.gateway.MocksData
import com.tech.building.gateway.material.mapper.MaterialsDtoToMaterialsModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MaterialDataSourceImpl(
    private val mapper: MaterialsDtoToMaterialsModelMapper
) : MaterialDataSource {
    override fun getMaterials(): Flow<List<MaterialModel>> {
        val list = MocksData.mockListMaterials()
        return flow {
            emit(mapper.map(list))
        }
    }
}