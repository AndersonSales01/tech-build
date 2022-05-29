package com.tech.building.features.newrequest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.tech.building.R
import com.tech.building.domain.model.CollaboratorModel
import kotlinx.android.synthetic.main.dropdown_collaborate_item.view.*

class CollaborateArrayAdapter(
    private val listener: (CollaboratorModel) -> Unit,
    context: Context,
    private val list: List<CollaboratorModel>
) : ArrayAdapter<CollaboratorModel>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): CollaboratorModel? {
        return list[position]
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val collaborate = getItem(position)

        val view =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.dropdown_collaborate_item, parent, false)
        collaborate?.let {
            view.nameCollaborate.text = collaborate?.name
            view.setOnClickListener {
                listener(collaborate)
            }
        }

        return view
    }
}