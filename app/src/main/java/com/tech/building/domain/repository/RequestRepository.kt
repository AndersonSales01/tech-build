package com.tech.building.domain.repository

import com.tech.building.domain.model.RequestModel
import kotlinx.coroutines.flow.Flow

interface RequestRepository {
    fun saveNewRequest(requestModel: RequestModel): Flow<Unit>
}