package com.tech.building.features.releaserequest.releasematerial.viewmodel

data class ReleaseRequestedMaterialUiState(
    val descriptionMaterialValue: String = "",
    val codeMaterialValue: String = "",
    val qtdRequestValue: String = "",
    val unitMaterialValue: String = "",
    val messageErrorQtdAttendedComponent: String = "",
)