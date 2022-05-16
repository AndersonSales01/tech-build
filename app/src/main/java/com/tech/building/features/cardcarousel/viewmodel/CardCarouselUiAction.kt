package com.tech.building.features.cardcarousel.viewmodel

sealed class CardCarouselUiAction {
    object NavigateToNewRequest : CardCarouselUiAction()
    object NavigateToReleaseRequest : CardCarouselUiAction()
}