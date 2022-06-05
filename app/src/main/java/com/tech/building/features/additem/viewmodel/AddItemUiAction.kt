package com.tech.building.features.additem.viewmodel

import com.tech.building.domain.model.ItemRequestModel

sealed class AddItemUiAction {
    data class ResultItemAdded(
        val itemRequestModel: ItemRequestModel
    ) : AddItemUiAction()
}