package com.tech.building.domain.usecase.cardcarousel

import com.tech.building.R
import com.tech.building.domain.model.CardCarouselModel
import com.tech.building.domain.model.CarouselCardsType
import com.tech.building.domain.model.UserProfile
import com.tech.building.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetCardsCarouselHasPermissionUseCase(
    private val repository: UserRepository
) {

    private val cardsCarousel = mutableListOf<CardCarouselModel>()

    operator fun invoke(): Flow<List<CardCarouselModel>> {
        val data = repository.getUserData()
        data?.let {
            if (data.userProfile == UserProfile.ADMIN.name) {
                createListWhenIsAdmin()
            } else {
                createListWhenNotIsAdmin(data.permissions)
            }
        }
        return flow {
            emit(cardsCarousel)
        }
    }

    private fun createListWhenIsAdmin() {
        cardsCarousel.add(
            CardCarouselModel(
                R.string.card_carousel_new_request,
                R.raw.registra,
                CarouselCardsType.NEW_REQUEST
            )

        )
        cardsCarousel.add(
            CardCarouselModel(
                R.string.card_carousel_release_request,
                R.raw.checkfile,
                CarouselCardsType.RELEASE_REQUEST
            )
        )
    }

    private fun createListWhenNotIsAdmin(permissions: List<String>) {
        permissions.forEach {
            when (it) {
                CarouselCardsType.NEW_REQUEST.type -> {
                    cardsCarousel.add(
                        CardCarouselModel(
                            R.string.card_carousel_new_request,
                            R.raw.registra,
                            CarouselCardsType.NEW_REQUEST
                        )
                    )
                }
                CarouselCardsType.RELEASE_REQUEST.type -> {
                    cardsCarousel.add(
                        CardCarouselModel(
                            R.string.card_carousel_release_request,
                            R.raw.checkfile,
                            CarouselCardsType.RELEASE_REQUEST
                        )
                    )
                }
            }
        }
    }
}