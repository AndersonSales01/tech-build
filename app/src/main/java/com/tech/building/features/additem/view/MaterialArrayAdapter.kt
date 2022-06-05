package com.tech.building.features.additem.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.tech.building.R
import com.tech.building.domain.model.MaterialModel
import kotlinx.android.synthetic.main.dropdown_collaborate_item.view.*

class MaterialArrayAdapter(
    private val listener: (MaterialModel) -> Unit,
    context: Context,
    private val list: List<MaterialModel>
) : ArrayAdapter<MaterialModel>(context, 0, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): MaterialModel? {
        return list[position]
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val material = getItem(position)

        val view =
            convertView ?: LayoutInflater.from(context)
                .inflate(R.layout.dropdown_collaborate_item, parent, false)
        material?.let {
            view.nameCollaborate.text = material?.name
            view.setOnClickListener {
                listener(material)
            }
        }

        return view
    }
}