package com.tech.building.features.additem.viewmodel

import com.tech.building.domain.model.MaterialModel

data class AddItemUiState(
    val materials: List<MaterialModel> = emptyList(),
    val codeMaterialSelected: String = "",
    val unitsMeasure: List<String> = emptyList(),
    val messageErrorRequestQtdComponent: String = "",
    val messageErrorSelectorMaterialComponent: String = "",
    val messageErrorUnitSelectorComponent: String = "",
    val setValueMaterialSelector: String = "",
    val setUnitSelected: String = "",
    val setQtd: String = "",
    val enableMaterialComponent: Boolean = true,
    val isEditItem: Boolean = false
)