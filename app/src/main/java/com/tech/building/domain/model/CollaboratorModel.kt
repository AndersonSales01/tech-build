package com.tech.building.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollaboratorModel(
    val name: String,
    val age: Int,
    val cpf: String,
    val registration: String
) : Parcelable