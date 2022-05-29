package com.tech.building.features.cardcarousel.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tech.building.R
import com.tech.building.domain.model.CardCarouselModel
import com.tech.building.features.cardcarousel.viewmodel.CardCarouselUiAction
import com.tech.building.features.cardcarousel.viewmodel.CardCarouselViewModel
import com.tech.building.features.newrequest.view.NewRequestActivity
import kotlinx.android.synthetic.main.fragment_card_carousel.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CardCarouselFragment : Fragment(R.layout.fragment_card_carousel) {

    private val viewModel: CardCarouselViewModel by viewModel()

    private val cardsAdapter = CardCarouselAdapter(
        listener = { viewModel.cardCarouselSelected(it) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setStateObserver()
        setActionObserver()
        viewModel.getCardsCarouselHasPermission()
    }

    private fun setStateObserver() {
        viewModel.stateLiveData.observe(requireActivity()) { state ->
            when {
                state.cardsCarousel.isNotEmpty() -> {
                    updateCardsList(state.cardsCarousel)
                }
            }
        }
    }

    private fun setActionObserver() {
        viewModel.actionLiveData.observe(requireActivity()) { action ->
            when (action) {
                is CardCarouselUiAction.NavigateToNewRequest -> navigateToNewRequestScreen()
                is CardCarouselUiAction.NavigateToReleaseRequest -> navigateToReleaseRequestScreen()
            }
        }
    }

    private fun navigateToNewRequestScreen() {
        startActivity(NewRequestActivity.newIntent(requireContext()))
    }

    private fun navigateToReleaseRequestScreen() {
        //TODO: Falta fazer chamada da tela de liberação.
    }

    private fun updateCardsList(cardsList: List<CardCarouselModel>) {
        cardsAdapter.submitList(cardsList)

        if (carouselCards.adapter == null) {
            with(carouselCards) {
                layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                adapter = cardsAdapter
            }
        }
    }

    companion object {
        fun newInstance(): Fragment {
            return CardCarouselFragment()
        }
    }
}