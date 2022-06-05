package com.tech.building.features.additem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.domain.model.MaterialModel
import com.tech.building.domain.usecase.material.GetMaterialsUseCase
import com.tech.building.features.utils.provider.ResourceProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class AddItemViewModel(
    private val getMaterialsUseCase: GetMaterialsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val resourceProvider: ResourceProvider
) : ViewModel() {
    private val stateMutableLiveData: MutableLiveData<AddItemUiState> = MutableLiveData()
    val stateLiveData: LiveData<AddItemUiState> = stateMutableLiveData

    private val actionMutableLiveData: MutableLiveData<AddItemUiAction> = MutableLiveData()
    val actionLiveData: LiveData<AddItemUiAction> = actionMutableLiveData

    private var materialSelected: MaterialModel? = null
    private var isRequestQtdValid = false
    private var isMaterialSelectorValid = false
    private var isUnitMeasureValid = false

    fun getMaterials() {
        viewModelScope.launch {
            getMaterialsUseCase.invoke()
                .flowOn(dispatcher)
                .onStart { }
                .onCompletion {}
                .collect {
                    getMaterialsHandleSuccess(it)
                }
        }
    }

    private fun getMaterialsHandleSuccess(materials: List<MaterialModel>) {
        if (materials.isNotEmpty()) {
            stateMutableLiveData.value = AddItemUiState(materials = materials)
        }
    }

    fun materialSelected(material: MaterialModel) {
        materialSelected = material
        material?.let {
            onSetMaterialCode(material.code)
            onSetUnitsMeasure(material.unitMeasure)
        }
    }

    private fun onSetMaterialCode(code: String) {
        if (code.isNotEmpty()) {
            stateMutableLiveData.value = AddItemUiState(codeMaterialSelected = code)
        }
    }

    private fun onSetUnitsMeasure(unitsMeasure: List<String>) {
        if (unitsMeasure.isNotEmpty()) {
            stateMutableLiveData.value = AddItemUiState(unitsMeasure = unitsMeasure)
        }
    }

    fun saveItemButton(
        requestQtd: Int,
        unit: String
    ) {
        checkRequestQtdComponent(requestQtd)
        checkSelectorMaterialComponent()
        checkUnitSelectorComponent(unit)

        if (isRequestQtdValid && isMaterialSelectorValid && isUnitMeasureValid) {
            resultItemAdded(requestQtd = requestQtd, unit = unit)
        }
    }

    private fun resultItemAdded(
        requestQtd: Int,
        unit: String
    ) {
        if (materialSelected != null && unit.isNotEmpty() && requestQtd > 0) {
            val item = ItemRequestModel(
                materialModel = materialSelected!!,
                unit = unit,
                qtdRequested = requestQtd.toDouble()
            )
            actionMutableLiveData.value = AddItemUiAction.ResultItemAdded(item)
        }
    }

    private fun checkUnitSelectorComponent(unit: String) {
        if (unit.isNotEmpty()) {
            isUnitMeasureValid = true
            stateMutableLiveData.value =
                AddItemUiState(messageErrorUnitSelectorComponent = "")
        } else {
            isUnitMeasureValid = false
            stateMutableLiveData.value =
                AddItemUiState(
                    messageErrorUnitSelectorComponent =
                    resourceProvider.getString(R.string.add_item_message_error_unit_selector)
                )
        }
    }

    private fun checkRequestQtdComponent(requestQtd: Int) {
        if (requestQtd > 0) {
            isRequestQtdValid = true
            stateMutableLiveData.value =
                AddItemUiState(messageErrorRequestQtdComponent = "")
        } else {
            isRequestQtdValid = false
            stateMutableLiveData.value =
                AddItemUiState(
                    messageErrorRequestQtdComponent =
                    resourceProvider.getString(R.string.add_item_message_error_qtd_requested)
                )
        }
    }

    private fun checkSelectorMaterialComponent() {
        materialSelected?.let {
            isMaterialSelectorValid = true
            stateMutableLiveData.value =
                AddItemUiState(messageErrorSelectorMaterialComponent = "")

        } ?: run {
            isMaterialSelectorValid = false
            stateMutableLiveData.value =
                AddItemUiState(
                    messageErrorSelectorMaterialComponent = resourceProvider.getString(
                        R.string.add_item_message_error_material_selector
                    )
                )
        }
    }
}