package com.tech.building.features.newrequest.additem.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.domain.model.MaterialModel
import com.tech.building.features.newrequest.additem.viewmodel.AddItemUiAction
import com.tech.building.features.newrequest.additem.viewmodel.AddItemViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_add_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_KEY = "argKey"

class AddItemActivity : AppCompatActivity(R.layout.activity_add_item) {

    private val viewModel: AddItemViewModel by viewModel()

    @Parcelize
    data class Args(
        val item: ItemRequestModel,
    ) : Parcelable

    private val data: Args? by lazy {
        intent?.getParcelableExtra<Args>(ARG_KEY)
    }

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

    override fun onResume() {
        super.onResume()
        viewModel.setItemForEdit(data)
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(this) { state ->
            when {
                state.setUnitSelected.isNotEmpty() -> {
                    setUnitSelected(state.setUnitSelected)
                }
                state.setValueMaterialSelector.isNotEmpty() -> {
                    setTextSelector(state.setValueMaterialSelector)
                }
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
                state.setQtd.isNotEmpty() -> {
                    setQtd(state.setQtd)
                }
                state.enableMaterialComponent.not()-> {
                    setMaterialComponentEnabled(state.enableMaterialComponent)
                }
                state.isEditItem-> {
                    isEditItem()
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
                is AddItemUiAction.ResultItemAdded -> resultItem(action.itemRequestModel,action.resultKey,action.resultCode)
            }
        }
    }

    private fun setupListener() {
        saveItemButton.setOnClickListener {
            viewModel.saveItemButton(
                requestQtd = requestQuantityValue.text.toString().toIntOrNull() ?: 0,
                unit = selectorUnit.text.toString(),
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
        viewModel.materialSelected(material)
    }

    private fun setTextSelector(value: String) {
        selectorMaterial.setText(value)
        selectorUnit.setText("")
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

    private fun setUnitSelected(unitSelected: String) {
        selectorUnit.setText(unitSelected)
    }

    private fun setQtd(qtd: String) {
        requestQuantityValue.setText(qtd)
    }

    private fun setMaterialComponentEnabled(enabled: Boolean) {
        materialSelectorLayout.isEnabled = enabled
    }

    private fun isEditItem() {
        saveItemButton.text = getString(R.string.add_item_screen_edit_button)
        topAppBar.title = getString(R.string.add_item_screen_toolbar_tile_edit_item)
    }

    private fun resultItem(
        itemRequest: ItemRequestModel,
        resultKey: String,
        resultCode: Int
    ) {
        setResult(resultCode, Intent().apply {
            putExtra(resultKey, itemRequest)
        })
        finish()
    }

    companion object {
        const val RESULT_CODE_ITEM_ADDED = 2000
        const val RESULT_CODE_CHANGE_ITEM = 2002
        const val ITEM_ADDED_KEY = "itemAdded"
        const val ITEM_EDIT_KEY = "changeItem"
        fun newInstance(args: Args?, context: Context): Intent {
            val intent = Intent(context, AddItemActivity::class.java)
            intent.putExtra(ARG_KEY, args)
            return intent
        }
    }
}