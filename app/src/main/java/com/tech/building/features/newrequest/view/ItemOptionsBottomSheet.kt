package com.tech.building.features.newrequest.view

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.item_options_bottom_sheet_content.*

class ItemOptionsBottomSheet : BottomSheetDialogFragment() {

    @Parcelize
    data class Args(
        val item: ItemRequestModel,
        val onEditItem: () -> Unit = {},
        val onRemoveItem: () -> Unit = {},
    ) : Parcelable

    private var arg: Args? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_options_bottom_sheet_content, container, false)
        val data: Args = arguments?.get(ITEM_REQUEST) as Args
        arg = data
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        arg?.let { argument ->
            editItem.setOnClickListener {
                argument.onEditItem.invoke()
                dismiss()
            }

            removeItem.setOnClickListener {
                argument.onRemoveItem.invoke()
                dismiss()
            }
        }
    }

    companion object {
        const val ITEM_REQUEST = "ItemOptionsBottomSheet"
        operator fun invoke(args: Args): ItemOptionsBottomSheet {
            val bottomSheet = ItemOptionsBottomSheet()
            val bundle = Bundle()
            bundle.putParcelable(ITEM_REQUEST, args)
            bottomSheet.arguments = bundle
            return bottomSheet
        }
    }
}