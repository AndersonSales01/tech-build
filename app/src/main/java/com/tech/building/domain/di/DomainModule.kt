package com.tech.building.domain.di

import com.tech.building.domain.usecase.cardcarousel.GetCardsCarouselHasPermissionUseCase
import com.tech.building.domain.usecase.collaborator.GetCollaboratorWithQrcodeUseCase
import com.tech.building.domain.usecase.collaborator.GetCollaboratorsUseCase
import com.tech.building.domain.usecase.login.LoginUseCase
import com.tech.building.domain.usecase.material.GetMaterialsUseCase
import com.tech.building.domain.usecase.network.HasInternetConnectionUseCase
import com.tech.building.domain.usecase.request.GetRequestsByFilterUseCase
import com.tech.building.domain.usecase.request.ReleaseRequestUseCase
import com.tech.building.domain.usecase.request.SendRequestUseCase
import org.koin.dsl.module

val domainModule = module {
    factory {
        LoginUseCase(
            repository = get()
        )
    }

    factory {
        GetCardsCarouselHasPermissionUseCase(
            repository = get()
        )
    }

    factory {
        GetCollaboratorsUseCase(
            repository = get()
        )
    }

    factory {
        GetCollaboratorWithQrcodeUseCase(
            repository = get()
        )
    }

    factory {
        GetMaterialsUseCase(
            repository = get()
        )
    }

    factory {
        SendRequestUseCase(
            repository = get()
        )
    }

    factory {
        GetRequestsByFilterUseCase(
            repository = get()
        )
    }

    factory {
        ReleaseRequestUseCase(
            repository = get()
        )
    }

    factory {
        HasInternetConnectionUseCase(
            networkConnectionInfo = get()
        )
    }
}