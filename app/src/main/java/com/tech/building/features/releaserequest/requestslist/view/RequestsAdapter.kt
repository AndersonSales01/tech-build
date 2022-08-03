package com.tech.building.features.releaserequest.requestslist.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tech.building.R
import com.tech.building.domain.model.RequestModel
import kotlinx.android.synthetic.main.request_item_layout.view.*

class RequestsAdapter(
    private val listenerItem: (RequestModel) -> Unit,
) : ListAdapter<RequestModel, RequestsAdapter.ItemListViewHolder>(DiffUtil()) {

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        holder.bindItem(getItem(position), listenerItem)
    }

    class DiffUtil : androidx.recyclerview.widget.DiffUtil.ItemCallback<RequestModel>() {
        override fun areItemsTheSame(
            oldItem: RequestModel,
            newItem: RequestModel
        ): Boolean =
            oldItem == newItem


        override fun areContentsTheSame(
            oldItem: RequestModel,
            newItem: RequestModel
        ): Boolean =
            oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        return LayoutInflater
            .from(parent.context)
            .inflate(R.layout.request_item_layout, parent, false)
            .run { ItemListViewHolder(this) }
    }

    inner class ItemListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindItem(item: RequestModel, listenerItem: (RequestModel) -> Unit) {
            view.collaborateName.text = item.collaborator.name
            view.statusValue.text = item.status.status
            view.setOnClickListener {
                listenerItem(item)
            }
        }
    }
}