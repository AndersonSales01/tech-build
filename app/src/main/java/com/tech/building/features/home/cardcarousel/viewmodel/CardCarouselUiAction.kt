package com.tech.building.features.home.cardcarousel.viewmodel

sealed class CardCarouselUiAction {
    object NavigateToNewRequest : CardCarouselUiAction()
    object NavigateToReleaseRequest : CardCarouselUiAction()
}