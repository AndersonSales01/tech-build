package com.tech.building.domain.usecase.collaborator

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.repository.CollaboratorRepository
import kotlinx.coroutines.flow.Flow

class GetCollaboratorWithQrcodeUseCase(
    private val repository: CollaboratorRepository
) {
    operator fun invoke(registration: String): Flow<CollaboratorModel?> {
        return repository.getCollaboratorWithQrcode(registration)
    }
}