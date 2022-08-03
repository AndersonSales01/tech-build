package com.tech.building.features.releaserequest.releaserequest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.features.releaserequest.releaserequest.view.ReleaseRequestActivity
import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity

class ReleaseRequestViewModel : ViewModel() {

    private val stateMutableLiveData: MutableLiveData<ReleaseRequestUiState> = MutableLiveData()
    val stateLiveData: LiveData<ReleaseRequestUiState> = stateMutableLiveData

    private val actionMutableLiveData: MutableLiveData<ReleaseRequestUiAction> =
        MutableLiveData()
    val actionLiveData: LiveData<ReleaseRequestUiAction> = actionMutableLiveData

    private var materials = mutableListOf<ItemRequestModel>()

    fun setupViews(arg: ReleaseRequestActivity.Args?) {
        arg?.let {
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
            materials.forEach {
                if (it.materialModel.code == item.materialModel.code) {
                    val index = materials.indexOf(it)
                    materials[index] = item
                }
            }
            loadMaterialList(materials)
        }
    }
}