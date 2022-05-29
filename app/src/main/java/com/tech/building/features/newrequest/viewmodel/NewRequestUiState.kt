package com.tech.building.features.newrequest.viewmodel

import com.tech.building.domain.model.CollaboratorModel

data class NewRequestUiState(
    val collaborators: List<CollaboratorModel> = emptyList()
)