package com.tech.building.features.scanqrcodecollaborate.viewmodel

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.usecase.collaborator.CheckCollaboratorValidUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QrcodeScanCollaborateViewModel(
    private val checkCollaboratorValidUseCase: CheckCollaboratorValidUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val actionMutableLiveData: MutableLiveData<QrcodeScanCollaborateUiAction> =
        MutableLiveData()
    val actionLiveData: LiveData<QrcodeScanCollaborateUiAction> = actionMutableLiveData

    private val stateMutableLiveData: MutableLiveData<QrcodeScanCollaborateUiState> =
        MutableLiveData()
    val stateLiveData: LiveData<QrcodeScanCollaborateUiState> = stateMutableLiveData

    fun checkPermission(context: Context) {
        if (hasCameraPermission(context)) {
            startCamera()
        } else {
            requestPermission()
        }
    }

    fun startCamera() {
        actionMutableLiveData.value = QrcodeScanCollaborateUiAction.StartCamera
    }

    private fun requestPermission() {
        actionMutableLiveData.value = QrcodeScanCollaborateUiAction.RequestPermission
    }

    private fun hasCameraPermission(context: Context) =
        ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

    fun qrCodeScannerValue(value: String) {
        if (value.isNotEmpty()) {
            checkCollaboratorValid(value)
        }
    }

    private fun checkCollaboratorValid(idRegistration: String) {
        viewModelScope.launch {
            checkCollaboratorValidUseCase.invoke(idRegistration)
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .collect {
                    checkCollaboratorValidHandleSuccess(it)
                }
        }
    }

    private fun checkCollaboratorValidHandleSuccess(collaborator: CollaboratorModel?) {
        if (collaborator != null) {
            actionMutableLiveData.value =
                QrcodeScanCollaborateUiAction.ScanSuccess(collaborator = collaborator)
        } else {
            stateMutableLiveData.value = QrcodeScanCollaborateUiState(hasError = true)
        }

    }
}