package com.tech.building.features.releaserequest.requestslist.viewmodel

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.RequestModel

data class RequestListUiState(
    val loadCollaborators: List<CollaboratorModel> = emptyList(),
    val loadRequests: List<RequestModel> = emptyList(),
)