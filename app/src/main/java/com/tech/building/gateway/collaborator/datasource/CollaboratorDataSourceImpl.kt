package com.tech.building.gateway.collaborator.datasource

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.gateway.MocksData
import com.tech.building.gateway.collaborator.mapper.CollaboratorDtoToCollaboratorModelMapper
import com.tech.building.gateway.collaborator.mapper.ListCollaboratorDtoToListCollaboratorModelMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class CollaboratorDataSourceImpl(
    private val mapper: ListCollaboratorDtoToListCollaboratorModelMapper,
    private val collaboratorDtoToCollaboratorModelMapper: CollaboratorDtoToCollaboratorModelMapper

) : CollaboratorDataSource {
    override fun getCollaborators(): Flow<List<CollaboratorModel>> {
        val list = MocksData.mockListCollaborators()
        return flow {
            emit(mapper.map(list))
        }
    }

    override fun getCollaboratorWithQrcode(registration: String): Flow<CollaboratorModel?> {
        val list = MocksData.mockListCollaborators()
        var result: CollaboratorModel? = null
        val collaboratorDto = list.filter { it.registration == registration }
        if (collaboratorDto.isNotEmpty()) {
            result = collaboratorDtoToCollaboratorModelMapper.map(collaboratorDto[0])

        }
        return flow {
            emit(result)
        }
    }
}