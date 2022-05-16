package com.tech.building.features.cardcarousel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.building.domain.model.CardCarouselModel
import com.tech.building.domain.model.CarouselCardsType
import com.tech.building.domain.usecase.cardcarousel.GetCardsCarouselHasPermissionUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class CardCarouselViewModel(
    private val getCardsCarouselUseCase: GetCardsCarouselHasPermissionUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val stateMutableLiveData: MutableLiveData<CardCarouselUiState> = MutableLiveData()
    val stateLiveData: LiveData<CardCarouselUiState> = stateMutableLiveData

    private val actionMutableLiveData: MutableLiveData<CardCarouselUiAction> = MutableLiveData()
    val actionLiveData: LiveData<CardCarouselUiAction> = actionMutableLiveData

    fun getCardsCarouselHasPermission() {
        viewModelScope.launch {
            getCardsCarouselUseCase.invoke()
                .flowOn(dispatcher)
                .collect {
                    onHandleSuccess(it)
                }
        }
    }

    private fun onHandleSuccess(cardsCarousel: List<CardCarouselModel>) {
        stateMutableLiveData.value = CardCarouselUiState(cardsCarousel = cardsCarousel)
    }

    fun cardCarouselSelected(card: CardCarouselModel) {
        when (card.type) {
            CarouselCardsType.NEW_REQUEST -> {
                actionMutableLiveData.value = CardCarouselUiAction.NavigateToNewRequest
            }
            CarouselCardsType.RELEASE_REQUEST -> {
                actionMutableLiveData.value = CardCarouselUiAction.NavigateToReleaseRequest
            }
        }
    }
}