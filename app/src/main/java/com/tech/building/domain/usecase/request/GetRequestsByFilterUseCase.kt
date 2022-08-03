package com.tech.building.domain.usecase.request

import com.tech.building.domain.model.FilterRequestStatus
import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow

class GetRequestsByFilterUseCase(
    private val repository: RequestRepository
) {
    operator fun invoke(
        registrationCollaborator: String,
        filter: FilterRequestStatus
    ): Flow<List<RequestModel>> =
        repository.getRequestsFiltered(
            registrationCollaborator = registrationCollaborator,
            filter = filter
        )
}