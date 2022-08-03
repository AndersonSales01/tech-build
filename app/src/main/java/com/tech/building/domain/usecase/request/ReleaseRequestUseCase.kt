package com.tech.building.domain.usecase.request

import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.repository.RequestRepository
import kotlinx.coroutines.flow.Flow

class ReleaseRequestUseCase(
    private val repository: RequestRepository
) {
    operator fun invoke(requestModel: RequestModel): Flow<Unit> =
        repository.releaseRequest(requestModel)
}