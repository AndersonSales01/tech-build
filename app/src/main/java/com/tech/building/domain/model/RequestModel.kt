package com.tech.building.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestModel(
    val id: Int = 0,
    val collaborator: CollaboratorModel,
    val itemsRequest: List<ItemRequestModel>,
    val status: RequestStatus
) : Parcelable