package com.tech.building.gateway.request.entity

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.ItemRequestModel

data class RequestDTO(
    val collaborator: CollaboratorModel,
    val itemsRequest: List<ItemRequestModel>,
    val status: String
)