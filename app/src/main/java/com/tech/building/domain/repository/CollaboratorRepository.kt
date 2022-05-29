package com.tech.building.domain.repository

import com.tech.building.domain.model.CollaboratorModel
import kotlinx.coroutines.flow.Flow

interface CollaboratorRepository {
    fun getCollaborators(): Flow<List<CollaboratorModel>>
}