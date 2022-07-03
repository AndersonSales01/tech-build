package com.tech.building.features.newrequest.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.tech.building.R
import com.tech.building.domain.model.CardCarouselModel
import com.tech.building.domain.model.ItemRequestModel

class ItemsAdapter(
    private val listenerItem: (ItemRequestModel) -> Unit,
) : ListAdapter<ItemRequestModel, ItemListViewHolder>(DiffUtil()) {

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_item_request_item, parent, false)
            .run { ItemListViewHolder(this) }
    }
}