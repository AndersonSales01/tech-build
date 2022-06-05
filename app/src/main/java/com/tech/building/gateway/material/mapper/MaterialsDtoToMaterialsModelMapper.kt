package com.tech.building.gateway.material.mapper

import com.tech.building.domain.model.MaterialModel
import com.tech.building.gateway.material.entity.MaterialDTO

class MaterialsDtoToMaterialsModelMapper {
    fun map(materialsDto: List<MaterialDTO>): List<MaterialModel> {
        val list = mutableListOf<MaterialModel>()
        materialsDto.map {
            list.add(
                MaterialModel(
                    name = it.name,
                    code = it.code,
                    unitMeasure = it.unitMeasure
                )
            )
        }
        return list
    }
}