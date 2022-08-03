package com.tech.building.features.home.cardcarousel.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tech.building.R
import com.tech.building.domain.model.CardCarouselModel
import kotlinx.android.synthetic.main.card_carousel_item.view.*

class CardCarouselAdapter(
    private val listener: (CardCarouselModel) -> Unit,
) :
    ListAdapter<CardCarouselModel, CardCarouselAdapter.ItemViewHolder>(CarouselItemsDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_carousel_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.configureView(getItem(position))
    }

    inner class ItemViewHolder(var view: View) : RecyclerView.ViewHolder(view) {


        fun configureView(cardCarousel: CardCarouselModel) {
            view.nameCard.text = view.context.getString(cardCarousel.name)
            view.animationCard.setAnimation(cardCarousel.animation)
            view.setOnClickListener { listener.invoke(cardCarousel) }
        }
    }
}

internal class CarouselItemsDiff : DiffUtil.ItemCallback<CardCarouselModel>() {
    override fun areItemsTheSame(oldItem: CardCarouselModel, newItem: CardCarouselModel): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: CardCarouselModel,
        newItem: CardCarouselModel
    ): Boolean {
        return oldItem == newItem
    }
}