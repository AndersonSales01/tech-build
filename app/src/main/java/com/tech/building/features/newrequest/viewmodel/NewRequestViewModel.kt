package com.tech.building.features.newrequest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.usecase.collaborator.GetCollaboratorsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewRequestViewModel(
    private val getCollaboratorsUseCase: GetCollaboratorsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val stateMutableLiveData: MutableLiveData<NewRequestUiState> = MutableLiveData()
    val stateLiveData: LiveData<NewRequestUiState> = stateMutableLiveData

    fun getCollaborators() {
        viewModelScope.launch {
            getCollaboratorsUseCase.invoke()
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .catch { getCollaboratorsHandleError() }
                .collect {
                    getCollaboratorsHandleSuccess(it)
                }
        }
    }

    private fun getCollaboratorsHandleSuccess(collaborators: List<CollaboratorModel>) {
        if (collaborators.isNotEmpty()) {
            stateMutableLiveData.value = NewRequestUiState(collaborators = collaborators)
        }
    }

    private fun getCollaboratorsHandleError() {

    }
}