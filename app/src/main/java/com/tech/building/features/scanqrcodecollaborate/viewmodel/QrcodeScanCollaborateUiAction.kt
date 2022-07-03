package com.tech.building.features.scanqrcodecollaborate.viewmodel

import com.tech.building.domain.model.CollaboratorModel

sealed class QrcodeScanCollaborateUiAction {
    object StartCamera : QrcodeScanCollaborateUiAction()
    object RequestPermission : QrcodeScanCollaborateUiAction()
    data class ScanSuccess(val collaborator: CollaboratorModel) : QrcodeScanCollaborateUiAction()
}