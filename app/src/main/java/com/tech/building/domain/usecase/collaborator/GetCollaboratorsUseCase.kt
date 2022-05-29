package com.tech.building.domain.usecase.collaborator

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.repository.CollaboratorRepository
import kotlinx.coroutines.flow.Flow

class GetCollaboratorsUseCase(
    private val repository: CollaboratorRepository
) {
    operator fun invoke(): Flow<List<CollaboratorModel>> =
        repository.getCollaborators()
}