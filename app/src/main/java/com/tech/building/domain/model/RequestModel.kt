package com.tech.building.domain.model

data class RequestModel(
    val collaborator: CollaboratorModel,
    val itemsRequest: List<ItemRequestModel>,
    val status: RequestStatus
)