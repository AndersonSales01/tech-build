package com.tech.building.features.releaserequest.releaserequest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tech.building.R
import com.tech.building.domain.model.ItemRequestModel
import kotlinx.android.synthetic.main.layout_material_request.view.*

class MaterialsAdapter(
    private val listenerItem: (ItemRequestModel) -> Unit,
) : ListAdapter<ItemRequestModel, MaterialsAdapter.MaterialListViewHolder>(DiffUtil()) {

    override fun onBindViewHolder(holder: MaterialListViewHolder, position: Int) {
        holder.bindItem(getItem(position), listenerItem)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<ItemRequestModel>() {
        override fun areItemsTheSame(
            oldItem: ItemRequestModel,
            newItem: ItemRequestModel
        ): Boolean =
            oldItem.materialModel == newItem.materialModel &&
                    oldItem.qtdRequested == newItem.qtdRequested &&
                    oldItem.unit == newItem.unit

        override fun areContentsTheSame(
            oldItem: ItemRequestModel,
            newItem: ItemRequestModel
        ): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialListViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_material_request, parent, false)
            .run { MaterialListViewHolder(this) }
    }

    inner class MaterialListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(item: ItemRequestModel, listenerItem: (ItemRequestModel) -> Unit) {
            view.materialName.text = item.materialModel.name
            view.qtdRequestedValue.text = item.qtdRequested.toString()
            view.qtdAttendedValue.text = item.quantityAttended.toString()
            view.unitValue.text = item.unit
            view.setOnClickListener {
                listenerItem(item)
            }
        }
    }
}