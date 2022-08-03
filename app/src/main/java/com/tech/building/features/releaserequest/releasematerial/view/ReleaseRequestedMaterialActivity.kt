package com.tech.building.features.releaserequest.releasematerial.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.features.releaserequest.releasematerial.viewmodel.ReleaseRequestedMaterialUiAction
import com.tech.building.features.releaserequest.releasematerial.viewmodel.ReleaseRequestedMaterialViewModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_release_material.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_KEY = "ReleaseMaterialActivity"

class ReleaseRequestedMaterialActivity : AppCompatActivity(R.layout.activity_release_material) {

    private val viewmodel: ReleaseRequestedMaterialViewModel by viewModel()

    @Parcelize
    data class Args(
        val item: ItemRequestModel,
    ) : Parcelable

    private val data: Args? by lazy {
        intent?.getParcelableExtra(ARG_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStateObserver()
        setActionObserver()
        setupListener()
        setupToolbar()
        viewmodel.setupViews(data)
    }

    private fun setupToolbar() {
        toolAppBar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setupListener() {
        saveMaterialButton.setOnClickListener {
            viewmodel.onSaveMaterialClicked(
                qtdAttended = qtdAttendedValue.text.toString().toIntOrNull() ?: 0,
            )
        }
    }

    private fun setStateObserver() {
        viewmodel.stateLiveData.observe(this) { state ->
            setDescriptionMaterial(state.descriptionMaterialValue)
            setCodeMaterial(state.codeMaterialValue)
            setQtdRequested(state.qtdRequestValue)
            setUnitMaterial(state.unitMaterialValue)
            setMessageErrorRequestQtdComponent(state.messageErrorQtdAttendedComponent)
        }
    }

    private fun setActionObserver() {
        viewmodel.actionLiveData.observe(this) { action ->
            when (action) {
                is ReleaseRequestedMaterialUiAction.ResultItemSaved -> resultItemSaved(
                    action.itemRequestModel,
                    action.resultKey,
                    action.resultCode
                )
            }
        }
    }

    private fun setDescriptionMaterial(description: String) {
        materialValue.text = description
    }

    private fun setCodeMaterial(code: String) {
        materialCodeValue.text = code
    }

    private fun setQtdRequested(qtd: String) {
        qtdRequestedValue.text = qtd
    }

    private fun setUnitMaterial(unit: String) {
        unitValue.text = unit
    }

    private fun setMessageErrorRequestQtdComponent(message: String) {
        qtdAttendedLayout.error = message
    }

    private fun resultItemSaved(
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
        const val MATERIAL_SAVED_KEY = "materialSaved"
        const val RESULT_SAVED_MATERIAL = 1001
        fun newInstance(args: Args?, context: Context): Intent {
            val intent = Intent(context, ReleaseRequestedMaterialActivity::class.java)
            intent.putExtra(ARG_KEY, args)
            return intent
        }
    }
}