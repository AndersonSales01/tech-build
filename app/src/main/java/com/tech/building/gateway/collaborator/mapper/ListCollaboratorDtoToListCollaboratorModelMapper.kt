package com.tech.building.gateway.collaborator.mapper

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.gateway.collaborator.entity.CollaboratorDTO

class ListCollaboratorDtoToListCollaboratorModelMapper {

    fun map(collaboratorsDto: List<CollaboratorDTO>): List<CollaboratorModel> {
        val list = mutableListOf<CollaboratorModel>()
        collaboratorsDto.map {
            list.add(
                CollaboratorModel(
                    name = it.name,
                    age = it.age,
                    cpf = it.cpf,
                    registration = it.registration
                )
            )
        }
        return list
    }
}