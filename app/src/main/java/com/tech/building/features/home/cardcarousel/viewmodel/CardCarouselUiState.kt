package com.tech.building.features.home.cardcarousel.viewmodel

import com.tech.building.domain.model.CardCarouselModel

data class CardCarouselUiState(
    val cardsCarousel: List<CardCarouselModel> = emptyList(),
)