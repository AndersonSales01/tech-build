package com.tech.building.gateway.collaborator.mapper

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.gateway.collaborator.entity.CollaboratorDTO

class CollaboratorDtoToCollaboratorModelMapper {

    fun map(collaboratorDTO: CollaboratorDTO): CollaboratorModel {
        return CollaboratorModel(
            name = collaboratorDTO.name,
            age = collaboratorDTO.age,
            cpf = collaboratorDTO.cpf,
            registration = collaboratorDTO.registration
        )
    }
}