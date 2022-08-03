package com.tech.building.domain.di

import com.tech.building.domain.usecase.cardcarousel.GetCardsCarouselHasPermissionUseCase
import com.tech.building.domain.usecase.collaborator.CheckCollaboratorValidUseCase
import com.tech.building.domain.usecase.collaborator.GetCollaboratorsUseCase
import com.tech.building.domain.usecase.login.LoginUseCase
import com.tech.building.domain.usecase.material.GetMaterialsUseCase
import com.tech.building.domain.usecase.request.GetRequestsByFilterUseCase
import com.tech.building.domain.usecase.request.SaveNewRequestUseCase
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
        CheckCollaboratorValidUseCase(
            repository = get()
        )
    }

    factory {
        GetMaterialsUseCase(
            repository = get()
        )
    }

    factory {
        SaveNewRequestUseCase(
            repository = get()
        )
    }

    factory {
        GetRequestsByFilterUseCase(
            repository = get()
        )
    }
}