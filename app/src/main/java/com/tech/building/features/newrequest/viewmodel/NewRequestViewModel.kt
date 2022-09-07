package com.tech.building.features.newrequest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.model.RequestStatus
import com.tech.building.domain.usecase.collaborator.GetCollaboratorsUseCase
import com.tech.building.domain.usecase.request.SendRequestUseCase
import com.tech.building.features.newrequest.additem.view.AddItemActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewRequestViewModel(
    private val getCollaboratorsUseCase: GetCollaboratorsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val sendRequestUseCase: SendRequestUseCase,
) : ViewModel() {

    private val stateMutableLiveData: MutableLiveData<NewRequestUiState> = MutableLiveData()
    val stateLiveData: LiveData<NewRequestUiState> = stateMutableLiveData

    private val actionMutableLiveData: MutableLiveData<NewRequestUiAction> = MutableLiveData()
    val actionLiveData: LiveData<NewRequestUiAction> = actionMutableLiveData

    private var items = mutableListOf<ItemRequestModel>()
    private var collaboratorSelected: CollaboratorModel? = null

    fun getCollaborators() {
        viewModelScope.launch {
            getCollaboratorsUseCase.invoke()
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .catch { getCollaboratorsHandleError() }
                .collect {
                    sendRequestHandleSuccess(it)
                }
        }
    }

    private fun sendRequestHandleSuccess(collaborators: List<CollaboratorModel>) {
        if (collaborators.isNotEmpty()) {
            stateMutableLiveData.value = NewRequestUiState(collaborators = collaborators)
        }
    }

    private fun getCollaboratorsHandleError() {

    }

    fun collaboratorSelected(collaboratorModel: CollaboratorModel) {
        collaboratorSelected = collaboratorModel
    }

    fun addItem(item: ItemRequestModel?) {
        item?.let {
            if (!checkIfItemAlreadyExists(item)) {
                items.add(item)
                loadItemsList()
            } else {
                stateMutableLiveData.value = NewRequestUiState(
                    isErrorItemAlreadyExists = true,
                    items = items
                )
            }
        }
    }

    fun editItem(item: ItemRequestModel?) {
        item?.let {
            updateItem(item)
        }
    }

    private fun loadItemsList() {
        stateMutableLiveData.value = NewRequestUiState(items = items)
    }

    private fun checkIfItemAlreadyExists(item: ItemRequestModel): Boolean {
        val itemModel = items.filter { it.materialModel.code == item.materialModel.code }
        if (itemModel.isNotEmpty()) {
            return true
        }
        return false
    }

    private fun updateItem(item: ItemRequestModel) {
        items.forEach {
            if (it.materialModel.code == item.materialModel.code) {
                val index = items.indexOf(it)
                items[index] = item
            }
        }
        loadItemsList()
    }

    fun sendRequest() {
        if (items.isNotEmpty()) {
            collaboratorSelected?.let {
                val requestModel = RequestModel(
                    collaborator = it,
                    status = RequestStatus.PENDING,
                    itemsRequest = items
                )

                sendRequest(requestModel)
                Log.d("sendRequest", "Colaborador: " + requestModel.collaborator.name)
                Log.d("sendRequest", "Status: " + requestModel.status.name)
                Log.d("sendRequest", "Items: " + requestModel.itemsRequest.size)
            }
        }
    }

    fun itemSelected(item: ItemRequestModel) {
        item?.let {
            actionMutableLiveData.value = NewRequestUiAction.OpeBottomSheetWithItemSelected(it)
        }
    }

    fun removeItemDialogOption(item: ItemRequestModel) {
        item?.let {
            items.remove(item)
        }
        loadItemsList()
    }

    fun editItemDialogOption(item: ItemRequestModel) {
        item?.let {
            val args = AddItemActivity.Args(
                item = it
            )
            openAddItemScreen(args)
        }
    }

    fun openAddItemScreen(args: AddItemActivity.Args? = null) {
        actionMutableLiveData.value = NewRequestUiAction.OpenAddItemScreen(args)
    }

    private fun sendRequest(requestModel: RequestModel) {
        viewModelScope.launch {
            sendRequestUseCase.invoke(requestModel)
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .collect {
                    sendRequestHandleSuccess()
                }
        }

    }

    private fun sendRequestHandleSuccess() {
        actionMutableLiveData.value = NewRequestUiAction.SendRequestSuccess
        Log.d("saveNewRequest", "Sucess: ")
    }
}