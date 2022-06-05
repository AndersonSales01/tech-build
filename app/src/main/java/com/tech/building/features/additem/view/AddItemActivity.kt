package com.tech.building.features.additem.view

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.domain.model.MaterialModel
import com.tech.building.features.additem.viewmodel.AddItemUiAction
import com.tech.building.features.additem.viewmodel.AddItemViewModel
import kotlinx.android.synthetic.main.activity_add_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RESULT_CODE_ITEM_ADDED = 2000
private const val ITEM_ADDED_KEY = "itemAdded"

class AddItemActivity : AppCompatActivity(R.layout.activity_add_item) {

    private val viewModel: AddItemViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStateObserver()
        setActionObserver()
        setupListener()
        setupToolbar()
        viewModel.getMaterials()
    }

    private fun setupToolbar() {
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(this) { state ->
            when {
                state.materials.isNotEmpty() -> {
                    setupSpinnerMaterials(state.materials)
                }
                state.codeMaterialSelected.isNotEmpty() -> {
                    setMaterialCode(state.codeMaterialSelected)
                }
                state.unitsMeasure.isNotEmpty() -> {
                    setupSpinnerUnitsMeasure(state.unitsMeasure)
                }
                state.messageErrorRequestQtdComponent.isNotEmpty() -> {
                    setMessageErrorRequestQtdComponent(state.messageErrorRequestQtdComponent)
                }
                state.messageErrorSelectorMaterialComponent.isNotEmpty() -> {
                    setMessageErrorMaterialSpinnerComponent(state.messageErrorSelectorMaterialComponent)
                }
                state.messageErrorUnitSelectorComponent.isNotEmpty() -> {
                    setMessageErrorUnitSpinnerComponent(state.messageErrorUnitSelectorComponent)
                }
                else -> {
                    setMessageErrorRequestQtdComponent(state.messageErrorRequestQtdComponent)
                    setMessageErrorMaterialSpinnerComponent(state.messageErrorSelectorMaterialComponent)
                    setMessageErrorUnitSpinnerComponent(state.messageErrorUnitSelectorComponent)
                }
            }
        }
    }

    private fun setActionObserver() {
        viewModel.actionLiveData.observe(this) { action ->
            when (action) {
                is AddItemUiAction.ResultItemAdded -> resultItem(action.itemRequestModel)
            }
        }
    }

    private fun setupListener() {
        saveItemButton.setOnClickListener {
            viewModel.saveItemButton(
                requestQtd = requestQuantityValue.text.toString().toIntOrNull() ?: 0,
                unit = selectorUnit.text.toString()
            )
        }
    }

    private fun setupSpinnerMaterials(materials: List<MaterialModel>) {
        val adapter = MaterialArrayAdapter(
            context = this,
            listener = { materialSelected(it) },
            list = materials
        )
        adapter.notifyDataSetChanged()
        selectorMaterial.setAdapter(adapter)
        selectorMaterial.threshold = Int.MAX_VALUE
    }


    private fun setupSpinnerUnitsMeasure(unitsMeasure: List<String>) {
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_collaborate_item, unitsMeasure)
        selectorUnit.setAdapter(arrayAdapter)
    }


    private fun materialSelected(material: MaterialModel) {
        selectorMaterial.setText(material.name)
        selectorUnit.setText("")
        viewModel.materialSelected(material)
    }

    private fun setMaterialCode(code: String) {
        materialCodeValue.text = code
    }

    private fun setMessageErrorRequestQtdComponent(message: String) {
        requestQuantityLayout.error = message
    }

    private fun setMessageErrorMaterialSpinnerComponent(message: String) {
        materialSelectorLayout.error = message
    }

    private fun setMessageErrorUnitSpinnerComponent(message: String) {
        unitSelectorLayout.error = message
    }

    private fun resultItem(
        itemRequest: ItemRequestModel
    ) {
        setResult(RESULT_CODE_ITEM_ADDED, Intent().apply {
            putExtra(ITEM_ADDED_KEY, itemRequest)
        })
        finish()
    }
}