package com.tech.building.features.releaserequest.requestslist.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.RequestModel
import com.tech.building.features.newrequest.view.CollaborateArrayAdapter
import com.tech.building.features.releaserequest.releaserequest.view.ReleaseRequestActivity
import com.tech.building.features.releaserequest.requestslist.viewmodel.RequestListUiAction
import com.tech.building.features.releaserequest.requestslist.viewmodel.RequestListViewModel
import kotlinx.android.synthetic.main.activity_list_request.*
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RequestListActivity::class.java)
        }
    }
}