package com.tech.building.features.newrequest.additem.viewmodel

import com.tech.building.domain.model.ItemRequestModel

sealed class AddItemUiAction {
    data class ResultItemAdded(
        val itemRequestModel: ItemRequestModel,
        val resultKey: String,
        val resultCode: Int
    ) : AddItemUiAction()
}