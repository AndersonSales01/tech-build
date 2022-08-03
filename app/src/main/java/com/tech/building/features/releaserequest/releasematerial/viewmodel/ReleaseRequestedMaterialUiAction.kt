package com.tech.building.features.releaserequest.releasematerial.viewmodel

import com.tech.building.domain.model.ItemRequestModel

sealed class ReleaseRequestedMaterialUiAction {
    data class ResultItemSaved(
        val itemRequestModel: ItemRequestModel,
        val resultKey: String,
        val resultCode: Int
    ) : ReleaseRequestedMaterialUiAction()
}