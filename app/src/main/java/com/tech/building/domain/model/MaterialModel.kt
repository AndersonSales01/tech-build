package com.tech.building.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MaterialModel(
    val name: String,
    val code: String,
    val unitMeasure: List<String>
) : Parcelable