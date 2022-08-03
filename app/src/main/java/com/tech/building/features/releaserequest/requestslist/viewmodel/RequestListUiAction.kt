package com.tech.building.features.releaserequest.requestslist.viewmodel

import com.tech.building.domain.model.RequestModel

sealed class RequestListUiAction {
    data class OpenReleaseRequestScreen(
        val request: RequestModel
    ) : RequestListUiAction()
}