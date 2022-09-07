package com.tech.building.features.releaserequest.releaserequest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.domain.model.RequestModel
import com.tech.building.domain.usecase.request.ReleaseRequestUseCase
import com.tech.building.features.releaserequest.releaserequest.view.ReleaseRequestActivity
import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ReleaseRequestViewModel(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val releaseRequestUseCase: ReleaseRequestUseCase
) : ViewModel() {

    private val stateMutableLiveData: MutableLiveData<ReleaseRequestUiState> = MutableLiveData()
    val stateLiveData: LiveData<ReleaseRequestUiState> = stateMutableLiveData

    private val actionMutableLiveData: MutableLiveData<ReleaseRequestUiAction> =
        MutableLiveData()
    val actionLiveData: LiveData<ReleaseRequestUiAction> = actionMutableLiveData

    private var materials = mutableListOf<ItemRequestModel>()

    private var request: RequestModel? = null

    fun setupViews(arg: ReleaseRequestActivity.Args?) {
        arg?.let {
            request = it.request
            materials.addAll(it.request.itemsRequest)
            loadMaterialList(it.request.itemsRequest)
        }
    }


    private fun loadMaterialList(list: List<ItemRequestModel>) {
        stateMutableLiveData.value = ReleaseRequestUiState(
            loadMaterials = list
        )
    }

    fun onItemClicked(item: ItemRequestModel) {
        val args = ReleaseRequestedMaterialActivity.Args(
            item = item
        )
        actionMutableLiveData.value = ReleaseRequestUiAction.OpenReleaseMaterialScreen(args)
    }

    fun updateItem(item: ItemRequestModel?) {
        item?.let {
            request?.let { request ->
                request.itemsRequest.filter { it.materialModel.code == item.materialModel.code }
                    .forEach { it.quantityAttended = item.quantityAttended }
                loadMaterialList(request.itemsRequest)
            }
        }
    }

    fun releaseRequestButtonClicked() {
        releaseRequest()
    }

    private fun releaseRequest() {
        request?.let {
            viewModelScope.launch {
                releaseRequestUseCase.invoke(
                    requestModel = it
                )
                    .flowOn(dispatcher)
                    .onStart { }
                    .onCompletion {}
                    .collect {
                        releaseRequestHandleSuccess()
                    }
            }
        }
    }

    private fun releaseRequestHandleSuccess() {
        actionMutableLiveData.value = ReleaseRequestUiAction.CloseScreen
        Log.d("releaseRequestUseCase", "Sucesso: ")
    }
}