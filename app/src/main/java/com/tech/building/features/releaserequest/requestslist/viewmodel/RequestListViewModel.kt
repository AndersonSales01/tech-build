package com.tech.building.features.releaserequest.requestslist.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.FilterRequestStatus
import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.usecase.collaborator.GetCollaboratorsUseCase
import com.tech.building.domain.usecase.request.GetRequestsByFilterUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RequestListViewModel(
    private val getCollaboratorsUseCase: GetCollaboratorsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getRequestsByFilterUseCase: GetRequestsByFilterUseCase,
) : ViewModel() {

    private val _uiStateFLow = MutableStateFlow(RequestListUiState())
    val listUiStateFlow: StateFlow<RequestListUiState> get() = _uiStateFLow

    private val actionMutableLiveDataList: MutableLiveData<RequestListUiAction> = MutableLiveData()
    val actionLiveDataList: LiveData<RequestListUiAction> = actionMutableLiveDataList

    private var collaboratorSelected: CollaboratorModel? = null

    fun getCollaborators() {
        viewModelScope.launch {
            getCollaboratorsUseCase.invoke()
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .collect {
                    getCollaboratorsHandleSuccess(it)
                }
        }
    }

    private fun getRequestsByFilter(
        registrationCollaborator: String = "",
        status: FilterRequestStatus = FilterRequestStatus.PENDING
    ) {
        viewModelScope.launch {
            getRequestsByFilterUseCase.invoke(
                registrationCollaborator = registrationCollaborator,
                filter = status
            )
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .collect {
                    getRequestsByFilterHandleSuccess(it)
                    Log.d("RequestStatus", "value: " + it)
                }
        }
    }

    private fun getRequestsByFilterHandleSuccess(requests: List<RequestModel>) {
        _uiStateFLow.value =
            RequestListUiState(loadRequests = requests)
    }

    private fun getCollaboratorsHandleSuccess(collaborators: List<CollaboratorModel>) {
        if (collaborators.isNotEmpty()) {
            _uiStateFLow.value =
                RequestListUiState(loadCollaborators = collaborators)
        }
    }

    fun onFilterRequestWithStatusChecked(idRadioButton: Int) {
        collaboratorSelected?.let {
            getRequestsByFilter(
                status = checkRequestStatusForFilter(idRadioButton),
                registrationCollaborator = it.registration
            )
        } ?: run {
            getRequestsByFilter(
                status = checkRequestStatusForFilter(idRadioButton)
            )
        }

        Log.d("RequestStatus", "value: " + checkRequestStatusForFilter(idRadioButton))
    }

    private fun checkRequestStatusForFilter(idRadioButton: Int): FilterRequestStatus {
        if (idRadioButton == R.id.checkboxPendingStatus) {
            return FilterRequestStatus.PENDING
        } else if (idRadioButton == R.id.checkboxAttendedStatus) {
            return FilterRequestStatus.ATTENDED
        }
        return FilterRequestStatus.ALL
    }

    fun onFilterWithCollaboratorSelected(
        statusChecked: Int,
        collaboratorModel: CollaboratorModel
    ) {
        collaboratorModel?.let {
            collaboratorSelected = collaboratorModel
            getRequestsByFilter(
                status = checkRequestStatusForFilter(statusChecked),
                registrationCollaborator = collaboratorModel.registration
            )
        }
    }

    fun onItemClicked(requestModel: RequestModel) {
        Log.d("onItemClicked", "value:" + requestModel)
        actionMutableLiveDataList.value = RequestListUiAction.OpenReleaseRequestScreen(requestModel)
    }
}