package com.tech.building.gateway.collaborator.repository

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.repository.CollaboratorRepository
import com.tech.building.gateway.collaborator.datasource.CollaboratorDataSource
import kotlinx.coroutines.flow.Flow

class CollaboratorRepositoryImpl(
    private val dataSource: CollaboratorDataSource
) : CollaboratorRepository {
    override fun getCollaborators(): Flow<List<CollaboratorModel>> =
        dataSource.getCollaborators()

    override fun getCollaboratorWithQrcode(registration: String): Flow<CollaboratorModel?> {
        return dataSource.getCollaboratorWithQrcode(registration)
    }
}