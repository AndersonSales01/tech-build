package com.tech.building.features.newrequest.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import com.tech.building.domain.model.ItemRequestModel
import com.tech.building.features.additem.view.AddItemActivity
import com.tech.building.features.additem.view.AddItemActivity.Companion.ITEM_ADDED_KEY
import com.tech.building.features.additem.view.AddItemActivity.Companion.ITEM_EDIT_KEY
import com.tech.building.features.additem.view.AddItemActivity.Companion.RESULT_CODE_CHANGE_ITEM
import com.tech.building.features.additem.view.AddItemActivity.Companion.RESULT_CODE_ITEM_ADDED
import com.tech.building.features.newrequest.viewmodel.NewRequestUiAction
import com.tech.building.features.newrequest.viewmodel.NewRequestViewModel
import com.tech.building.features.scanqrcodecollaborate.view.QrcodeScanCollaborateActivity
import kotlinx.android.synthetic.main.activity_new_request.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val RESULT_CODE_COLLABORATOR_SCANNED = 2001
private const val COLLABORATOR_SCANNED_KEY = "collaboratorScanned"
private const val BOTTOM_SHEET_KEY = "bottomSheetFragment"

class NewRequestActivity : AppCompatActivity(R.layout.activity_new_request) {

    private val viewModel: NewRequestViewModel by viewModel()

    private val itemsAdapter = ItemsAdapter(
        listenerItem = { viewModel.itemSelected(it) }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStateObserver()
        setActionObserver()
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
            viewModel.openAddItemScreen()
        }
        qrcodeScan.setOnClickListener {
            val intent = Intent(this, QrcodeScanCollaborateActivity::class.java)
            itemAddedForResult.launch(intent)
        }
        sendRequestButton.setOnClickListener {
            viewModel.sendRequest()
        }
    }

    private fun openAddItemScreen(args: AddItemActivity.Args?) {
        val intent = AddItemActivity.newInstance(
            context = this,
            args = args
        )
        itemAddedForResult.launch(intent)
    }

    private val itemAddedForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode) {
            RESULT_CODE_ITEM_ADDED -> {
                val item: ItemRequestModel? = result.data?.extras?.getParcelable(ITEM_ADDED_KEY)
                viewModel.addItem(item)
            }
            RESULT_CODE_CHANGE_ITEM -> {
                val item: ItemRequestModel? = result.data?.extras?.getParcelable(ITEM_EDIT_KEY)
                viewModel.editItem(item)
            }
            RESULT_CODE_COLLABORATOR_SCANNED -> {
                val collaborator: CollaboratorModel? =
                    result.data?.extras?.getParcelable(COLLABORATOR_SCANNED_KEY)
                collaborator?.let {
                    collaboratorSelected(collaborator)
                }
            }

        }
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(this) { state ->
            when {
                state.isErrorItemAlreadyExists -> {
                    itemAlreadyExistsError()
                }
                state.collaborators.isNotEmpty() -> {
                    setupSpinnerCollaborators(state.collaborators)
                }
                state.items.isNotEmpty() -> {
                    setItemList(state.items)
                }
                else -> {
                    setItemList(state.items)
                }

            }
        }
    }

    private fun setActionObserver() {
        viewModel.actionLiveData.observe(this) { action ->
            when (action) {
                is NewRequestUiAction.OpeBottomSheetWithItemSelected -> openBottomSheetItemSelected(
                    action.item
                )
                is NewRequestUiAction.OpenAddItemScreen -> openAddItemScreen(action.args)
            }
        }
    }

    private fun openBottomSheetItemSelected(item: ItemRequestModel) {
        val args = ItemOptionsBottomSheet.Args(
            item = item,
            onEditItem = { editItemDialogOption(item) },
            onRemoveItem = { removeItemDialogOption(item) },
        )
        ItemOptionsBottomSheet(args).show(supportFragmentManager, BOTTOM_SHEET_KEY)
    }


    private fun editItemDialogOption(item: ItemRequestModel) {
        viewModel.editItemDialogOption(item)
    }

    private fun removeItemDialogOption(item: ItemRequestModel) {
        viewModel.removeItemDialogOption(item)
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
        viewModel.collaboratorSelected(collaborator)
        edtCollaborator.setText(collaborator.name)
    }

    private fun setItemList(items: List<ItemRequestModel>) {
        itemsAdapter.submitList(items)
        itemsRecyclerView.adapter = itemsAdapter
    }

    private fun itemAlreadyExistsError() {
        Snackbar.make(
            rootNewRequest,
            R.string.new_request_screen_item_already_exists,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewRequestActivity::class.java)
        }
    }
}