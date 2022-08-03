package com.tech.building.gateway.request.mapper

import com.tech.building.domain.model.RequestModel
import com.tech.building.gateway.request.entity.RequestDTO

class RequestModelToRequestDtoMapper {
    fun map(request: RequestModel): RequestDTO {
        return RequestDTO(
            request.id,
            request.collaborator,
            request.itemsRequest,
            request.status.name
        )
    }
}