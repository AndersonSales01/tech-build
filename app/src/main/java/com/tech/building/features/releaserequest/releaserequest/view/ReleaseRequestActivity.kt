package com.tech.building.features.releaserequest.releaserequest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.domain.model.RequestModel
import com.tech.building.features.releaserequest.releasematerial.view.ReleaseRequestedMaterialActivity
import com.tech.building.features.releaserequest.releaserequest.viewmodel.ReleaseRequestUiAction
import com.tech.building.features.releaserequest.releaserequest.viewmodel.ReleaseRequestViewModel
import com.tech.building.features.utils.provider.NetWorkErrorPage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_release_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val ARG_KEY = "ReleaseRequestActivity"

class ReleaseRequestActivity : AppCompatActivity(R.layout.activity_release_request) {

    private val viewModel: ReleaseRequestViewModel by viewModel()

    private val materialsAdapter = MaterialsAdapter(
        listenerItem = { viewModel.onItemClicked(it) }
    )

    @Parcelize
    data class Args(
        val request: RequestModel,
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
        viewModel.setupViews(data)

    }


    private fun setupToolbar() {
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setupListener() {
        releaseRequestButton.setOnClickListener {
            viewModel.releaseRequestButtonClicked()
        }
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(this) { state ->
            when {
                state.loadMaterials.isNotEmpty() -> {
                    loadMaterialsList(state.loadMaterials)
                }
            }
        }
    }

    private fun setActionObserver() {
        viewModel.actionLiveData.observe(this) { action ->
            when (action) {
                is ReleaseRequestUiAction.OpenReleaseMaterialScreen -> {
                    openReleaseMaterialScreen(action.arg)
                }
                is ReleaseRequestUiAction.CloseScreen -> {
                    finish()
                }
                is ReleaseRequestUiAction.ShowNetWorkErrorPage -> {
                    showNetWorkErrorPage()
                }
            }
        }
    }


    private fun loadMaterialsList(materials: List<ItemRequestModel>) {
        materialsAdapter.submitList(materials)
        itemsRecyclerViewRelease.adapter = materialsAdapter
    }

    private fun openReleaseMaterialScreen(args: ReleaseRequestedMaterialActivity.Args?) {
        val intent = ReleaseRequestedMaterialActivity.newInstance(
            context = this,
            args = args
        )
        releaseMaterialForResult.launch(intent)
    }

    private val releaseMaterialForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            ReleaseRequestedMaterialActivity.RESULT_SAVED_MATERIAL -> {
                val item: ItemRequestModel? =
                    result.data?.extras?.getParcelable(ReleaseRequestedMaterialActivity.MATERIAL_SAVED_KEY)
                viewModel.updateItem(item)
            }
        }
    }

    private fun showNetWorkErrorPage() {
        val args = NetWorkErrorPage.Args(
            onTryAgain = { viewModel.releaseRequestButtonClicked() }
        )
        NetWorkErrorPage(args).show(supportFragmentManager, this::class.java.name)
    }


    companion object {
        fun newInstance(args: Args?, context: Context): Intent {
            val intent = Intent(context, ReleaseRequestActivity::class.java)
            intent.putExtra(ARG_KEY, args)
            return intent
        }
    }
}