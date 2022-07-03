package com.tech.building.features.newrequest.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tech.building.domain.model.ItemRequestModel
import kotlinx.android.synthetic.main.layout_item_request_item.view.*

class ItemListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bindItem(item: ItemRequestModel, listenerItem: (ItemRequestModel) -> Unit) {
        view.materialName.text = item.materialModel.name
        view.qtdValue.text = item.qtdRequested.toString()
        view.unitValue.text = item.unit
        view.setOnClickListener {
            listenerItem(item)
        }
    }
}