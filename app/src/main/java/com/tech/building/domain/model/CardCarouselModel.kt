package com.tech.building.domain.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class CardCarouselModel(
    @StringRes
    val name: Int,
    @RawRes
    val animation: Int,
    val type: CarouselCardsType
)

enum class CarouselCardsType(val type: String) {
    NEW_REQUEST("new_request"),
    RELEASE_REQUEST("release_request")
}