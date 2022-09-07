package com.tech.building.features.releaserequest.requestslist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.RequestModel
import com.tech.building.features.newrequest.view.CollaborateArrayAdapter
import com.tech.building.features.releaserequest.releaserequest.view.ReleaseRequestActivity
import com.tech.building.features.releaserequest.requestslist.viewmodel.RequestListUiAction
import com.tech.building.features.releaserequest.requestslist.viewmodel.RequestListViewModel
import com.tech.building.features.scanqrcodecollaborate.view.QrcodeScanCollaborateActivity
import com.tech.building.features.utils.provider.NetWorkErrorPage
import kotlinx.android.synthetic.main.activity_list_request.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RESULT_CODE_COLLABORATOR_SCANNED = 2001
private const val COLLABORATOR_SCANNED_KEY = "collaboratorScanned"

class RequestListActivity : AppCompatActivity(R.layout.activity_list_request) {

    private val listViewModel: RequestListViewModel by viewModel()
    private val requestsAdapter: RequestsAdapter by lazy {
        RequestsAdapter(
            listenerItem = { listViewModel.onItemClicked(it) }
        )
    }
    private var statusChecked: Int = R.id.checkboxPendingStatus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViews()
        setStateObserver()
        setActionObserver()
        setupToolbar()
        setupListener()
    }

    private fun setupListener() {
        qrcodeScanCollaborator.setOnClickListener {
            val intent = Intent(this, QrcodeScanCollaborateActivity::class.java)
            activityForResult.launch(intent)
        }
    }

    private fun setupToolbar() {
        toolAppBarRequestList.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("onResume", "Entrou")
        listViewModel.getCollaborators()
        listViewModel.onFilterRequestWithStatusChecked(statusChecked)
    }

    private fun setStateObserver() {
        lifecycleScope.launchWhenStarted {
            listViewModel.listUiStateFlow.collect { uiState ->
                when {
                    uiState.loadCollaborators.isNotEmpty() -> {
                        setupSpinnerCollaborators(uiState.loadCollaborators)
                    }
                    else -> {
                        loadRequestsList(uiState.loadRequests)
                    }
                }
            }
        }
    }

    private fun setActionObserver() {
        listViewModel.actionLiveDataList.observe(this) { action ->
            when (action) {
                is RequestListUiAction.OpenReleaseRequestScreen -> openReleaseRequestScreen(action.request)
                is RequestListUiAction.ShowNetWorkErrorPage -> showNetWorkErrorPage()
            }
        }
    }


    private val activityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_CODE_COLLABORATOR_SCANNED -> {
                val collaborator: CollaboratorModel? =
                    result.data?.extras?.getParcelable(COLLABORATOR_SCANNED_KEY)
                collaborator?.let {
                    collaboratorSelected(collaborator)
                }
            }

        }
    }

    private fun setupViews() {
        statusGroup.setOnCheckedChangeListener { radioGroup, idRadioButton ->
            statusChecked = idRadioButton
            listViewModel.onFilterRequestWithStatusChecked(idRadioButton)
        }
    }

    private fun setupSpinnerCollaborators(collaborators: List<CollaboratorModel>) {
        val adapter = CollaborateArrayAdapter(
            context = this,
            listener = { collaboratorSelected(it) },
            list = collaborators
        )
        adapter.notifyDataSetChanged()
        selectCollaborator.setAdapter(adapter)
        selectCollaborator.threshold = Int.MAX_VALUE
    }

    private fun collaboratorSelected(collaborator: CollaboratorModel) {
        listViewModel.onFilterWithCollaboratorSelected(
            statusChecked = statusChecked,
            collaboratorModel = collaborator
        )
        selectCollaborator.setText(collaborator.name)
    }

    private fun loadRequestsList(requests: List<RequestModel>) {
        requestsAdapter.submitList(requests)
        requestsRecyclerView.adapter = requestsAdapter
    }

    private fun openReleaseRequestScreen(request: RequestModel) {
        val args = ReleaseRequestActivity.Args(
            request = request
        )
        startActivity(ReleaseRequestActivity.newInstance(args = args, context = this))
    }

    private fun showNetWorkErrorPage() {
        val args = NetWorkErrorPage.Args(
            onTryAgain = { listViewModel.onFilterRequestWithStatusChecked(statusChecked) },
            onClosed = { finish() }
        )
        NetWorkErrorPage(args).show(supportFragmentManager, this::class.java.name)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RequestListActivity::class.java)
        }
    }
}