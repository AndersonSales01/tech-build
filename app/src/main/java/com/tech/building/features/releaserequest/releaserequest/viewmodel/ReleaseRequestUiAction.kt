package com.tech.building.features.releaserequest.releaserequest.viewmodel

import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity

sealed class ReleaseRequestUiAction {
    data class OpenReleaseMaterialScreen(
        val arg: ReleaseRequestedMaterialActivity.Args
    ) : ReleaseRequestUiAction()

    object CloseScreen : ReleaseRequestUiAction()
}