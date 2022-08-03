package com.tech.building.gateway.request.repository

import com.tech.building.domain.model.FilterRequestStatus
import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.repository.RequestRepository
import com.tech.building.gateway.request.datasource.RequestDataSource
import kotlinx.coroutines.flow.Flow

class RequestRepositoryImpl(
    private val dataSource: RequestDataSource
) : RequestRepository {
    override fun saveNewRequest(requestModel: RequestModel): Flow<Unit> {
        return dataSource.saveNewRequest(requestModel)
    }

    override fun getRequestsFiltered(
        registrationCollaborator: String,
        filter: FilterRequestStatus
    ): Flow<List<RequestModel>> {
        return dataSource.getRequestsFiltered(
            registrationCollaborator = registrationCollaborator,
            filter = filter
        )
    }

    override fun releaseRequest(requestModel: RequestModel): Flow<Unit> {
        return dataSource.releaseRequest(requestModel)
    }
}