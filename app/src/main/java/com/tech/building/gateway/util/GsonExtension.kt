package com.tech.building.gateway.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}

internal inline fun <reified T> Gson.convertJson(json: String) =
    fromJson<T>(json, object : TypeToken<T>() {}.type)

fun <T> T.toJson(): String {
    return Gson().toJson(this)
}