package com.tech.building.features.releaserequest.releaserequest.viewmodel

import com.tech.building.domain.model.ItemRequestModel

data class ReleaseRequestUiState(
    val loadMaterials: List<ItemRequestModel> = emptyList(),
)