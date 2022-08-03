package com.tech.building.gateway.request.mapper

import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.model.RequestStatus
import com.tech.building.gateway.request.entity.RequestDTO

class ListRequestDtoToListRequestModelMapper {

    fun map(requestsDTO: List<RequestDTO>): List<RequestModel> {
        val list = mutableListOf<RequestModel>()
        requestsDTO.map {
            list.add(
                RequestModel(
                    id = it.id,
                    collaborator = it.collaborator,
                    itemsRequest = it.itemsRequest,
                    status = it.status.getRequestStatus()
                )

            )
        }
        return list
    }

    private fun String.getRequestStatus(): RequestStatus {
        if (this == RequestStatus.PENDING.name) {
            return RequestStatus.PENDING
        }
        return RequestStatus.RELEASED
    }
}