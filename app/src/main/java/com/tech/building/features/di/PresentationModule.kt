package com.tech.building.features.di

import com.tech.building.features.additem.viewmodel.AddItemViewModel
import com.tech.building.features.cardcarousel.viewmodel.CardCarouselViewModel
import com.tech.building.features.login.viewmodel.LoginViewModel
import com.tech.building.features.newrequest.viewmodel.NewRequestViewModel
import com.tech.building.features.releaserequest.releaserequest.viewmodel.ReleaseRequestViewModel
import com.tech.building.features.releaserequest.releasematerial.viewmodel.ReleaseRequestedMaterialViewModel
import com.tech.building.features.releaserequest.requestslist.viewmodel.RequestListViewModel
import com.tech.building.features.scanqrcodecollaborate.viewmodel.QrcodeScanCollaborateViewModel
import com.tech.building.features.utils.provider.ResourceProvider
import com.tech.building.features.utils.provider.ResourceProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    factory<ResourceProvider> {
        ResourceProviderImpl(
            context = get()
        )
    }

    viewModel {
        LoginViewModel(
            loginUseCase = get()
        )
    }

    viewModel {
        CardCarouselViewModel(
            getCardsCarouselUseCase = get()
        )
    }

    viewModel {
        NewRequestViewModel(
            getCollaboratorsUseCase = get(),
            saveNewRequestUseCase = get()
        )
    }

    viewModel {
        AddItemViewModel(
            getMaterialsUseCase = get(),
            resourceProvider = get()
        )
    }

    viewModel {
        QrcodeScanCollaborateViewModel(
            checkCollaboratorValidUseCase = get()
        )
    }

    viewModel {
        RequestListViewModel(
            getCollaboratorsUseCase = get(),
            getRequestsByFilterUseCase = get()
        )
    }

    viewModel {
        ReleaseRequestViewModel()
    }

    viewModel {
        ReleaseRequestedMaterialViewModel()
    }
}