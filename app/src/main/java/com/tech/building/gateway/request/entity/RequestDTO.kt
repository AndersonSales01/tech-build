package com.tech.building.gateway.request.entity

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.ItemRequestModel

data class RequestDTO(
    val id: Int,
    val collaborator: CollaboratorModel,
    val itemsRequest: List<ItemRequestModel>,
    var status: String
)