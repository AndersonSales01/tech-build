package com.tech.building.features.releaserequest.releasematerial.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity
import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity.Companion.MATERIAL_SAVED_KEY
import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity.Companion.RESULT_SAVED_MATERIAL

class ReleaseRequestedMaterialViewModel : ViewModel() {

    private val stateMutableLiveData: MutableLiveData<ReleaseRequestedMaterialUiState> = MutableLiveData()
    val stateLiveData: LiveData<ReleaseRequestedMaterialUiState> = stateMutableLiveData

    private val actionMutableLiveData: MutableLiveData<ReleaseRequestedMaterialUiAction> = MutableLiveData()
    val actionLiveData: LiveData<ReleaseRequestedMaterialUiAction> = actionMutableLiveData

    private var item: ItemRequestModel? = null

    fun setupViews(args: ReleaseRequestedMaterialActivity.Args?) {
        args?.let {
            item = it.item
            stateMutableLiveData.value = ReleaseRequestedMaterialUiState(
                descriptionMaterialValue = it.item.materialModel.name,
                codeMaterialValue = it.item.materialModel.code,
                qtdRequestValue = it.item.qtdRequested.toString(),
                unitMaterialValue = it.item.unit
            )
        }
    }

    fun onSaveMaterialClicked(qtdAttended: Int) {
        item?.let {
            if (qtdAttended > 0) {
                if (qtdAttended <= it.qtdRequested) {
                    it.quantityAttended = qtdAttended
                    resultItemSaved(it)
                } else {
                    setMessageErrorQtdAttendedComponent("Voce não pode ultrapassar a quantidade solicitada.")
                }

            } else {
                setMessageErrorQtdAttendedComponent("Precisa adicionar quantidade atendida.")
            }
        }
    }

    private fun resultItemSaved(item: ItemRequestModel) {
        actionMutableLiveData.value = ReleaseRequestedMaterialUiAction.ResultItemSaved(
            itemRequestModel = item,
            resultKey = MATERIAL_SAVED_KEY,
            resultCode = RESULT_SAVED_MATERIAL
        )
    }

    private fun setMessageErrorQtdAttendedComponent(msg: String) {
        stateMutableLiveData.value =
            stateMutableLiveData.value?.copy(
                messageErrorQtdAttendedComponent = msg
            )
    }
}