package com.tech.building.domain.usecase.network

import com.tech.building.gateway.util.NetworkConnectionInfo

class HasInternetConnectionUseCase(
    private val networkConnectionInfo: NetworkConnectionInfo
) {
    operator fun invoke(): Boolean =
        networkConnectionInfo.hasInternetConnection()
}