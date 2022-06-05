package com.tech.building.features.utils.provider

import android.content.Context
import androidx.annotation.StringRes

class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {

    override fun getString(@StringRes resId: Int): String =
        context.resources.getString(resId)
}