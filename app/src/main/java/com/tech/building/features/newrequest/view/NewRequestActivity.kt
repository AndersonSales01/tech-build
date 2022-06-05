package com.tech.building.features.newrequest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.features.additem.view.AddItemActivity
import com.tech.building.features.newrequest.viewmodel.NewRequestViewModel
import kotlinx.android.synthetic.main.activity_new_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RESULT_CODE_ITEM_ADDED = 2000
private const val ITEM_ADDED_KEY = "itemAdded"

class NewRequestActivity : AppCompatActivity(R.layout.activity_new_request) {

    private val viewModel: NewRequestViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStateObserver()
        setupToolbar()
        setupListener()
        viewModel.getCollaborators()
    }

    private fun setupToolbar() {
        topAppBar.setNavigationOnClickListener {
            onBackPressed()
            finish()
        }
    }

    private fun setupListener() {
        addItemButton.setOnClickListener {
            val intent = Intent(this, AddItemActivity::class.java)
            itemAddedForResult.launch(intent)
        }
    }

    private val itemAddedForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_CODE_ITEM_ADDED) {
            val item: ItemRequestModel? = result.data?.extras?.getParcelable(ITEM_ADDED_KEY)
            Toast.makeText(this, item?.materialModel?.name, Toast.LENGTH_LONG).show()
        }
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(this) { state ->
            when {
                state.collaborators.isNotEmpty() -> {
                    setupSpinnerCollaborators(state.collaborators)
                }
            }
        }
    }

    private fun setupSpinnerCollaborators(collaborators: List<CollaboratorModel>) {
        val adapter = CollaborateArrayAdapter(
            context = this,
            listener = { collaboratorSelected(it) },
            list = collaborators
        )
        adapter.notifyDataSetChanged()
        edtCollaborator.setAdapter(adapter)
        edtCollaborator.threshold = Int.MAX_VALUE
    }

    private fun collaboratorSelected(collaborator: CollaboratorModel) {
        edtCollaborator.setText(collaborator.name)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewRequestActivity::class.java)
        }
    }
}