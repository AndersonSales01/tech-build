package com.tech.building.features.newrequest.viewmodel

import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.ItemRequestModel

data class NewRequestUiState(
    val collaborators: List<CollaboratorModel> = emptyList(),
    val items: List<ItemRequestModel> = emptyList(),
    val isErrorItemAlreadyExists: Boolean = false
)