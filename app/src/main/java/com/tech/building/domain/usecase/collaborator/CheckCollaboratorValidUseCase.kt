package com.tech.building.domain.usecase.collaborator

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.repository.CollaboratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CheckCollaboratorValidUseCase(
    private val repository: CollaboratorRepository
) {
    suspend operator fun invoke(idRegistration: String): Flow<CollaboratorModel?> =
        isCollaboratorValid(idRegistration)

    private suspend fun isCollaboratorValid(idRegistration: String): Flow<CollaboratorModel?> {
        var result: CollaboratorModel? = null
        getCollaborators().collect { collaborators ->
            val collaborator = collaborators.filter { it.registration == idRegistration }
            if (collaborator.isNotEmpty()) {
                result = collaborator[0]
            }
        }
        return flow {
            emit(result)
        }
    }

    private fun getCollaborators() =
        repository.getCollaborators()

}