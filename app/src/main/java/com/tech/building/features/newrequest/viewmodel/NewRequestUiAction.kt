package com.tech.building.features.newrequest.viewmodel

import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.features.additem.view.AddItemActivity

sealed class NewRequestUiAction {
    data class OpeBottomSheetWithItemSelected(
        val item: ItemRequestModel
    ) : NewRequestUiAction()

    data class OpenAddItemScreen(
        val args: AddItemActivity.Args? = null
    ) : NewRequestUiAction()
}