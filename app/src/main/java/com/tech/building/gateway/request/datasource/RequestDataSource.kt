package com.tech.building.gateway.request.datasource

import com.tech.building.domain.model.FilterRequestStatus
import com.tech.building.domain.model.RequestModel
import kotlinx.coroutines.flow.Flow

interface RequestDataSource {
    fun saveNewRequest(requestModel: RequestModel): Flow<Unit>
    fun getRequestsFiltered(
        registrationCollaborator: String,
        filter: FilterRequestStatus
    ): Flow<List<RequestModel>>
}