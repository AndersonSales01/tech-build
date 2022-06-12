package com.tech.building.gateway.collaborator.datasource

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.gateway.MocksData
import com.tech.building.gateway.collaborator.mapper.ListCollaboratorDtoToListCollaboratorModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CollaboratorDataSourceImpl(
    private val mapper: ListCollaboratorDtoToListCollaboratorModelMapper

) : CollaboratorDataSource {
    override fun getCollaborators(): Flow<List<CollaboratorModel>> {
        val list = MocksData.mockListCollaborators()
        return flow {
            emit(mapper.map(list))
        }
    }
}