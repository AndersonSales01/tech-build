package com.tech.building.gateway.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.core.content.getSystemService

class NetworkConnectionInfoImpl(private val context: Context): NetworkConnectionInfo {

    override fun hasInternetConnection(): Boolean {
        val connectivityManager: ConnectivityManager? = context.getSystemService()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork: Network? = connectivityManager?.activeNetwork
            val networkCapabilities: NetworkCapabilities? = connectivityManager?.getNetworkCapabilities(activeNetwork)
            if (networkCapabilities == null) {
                false
            } else {
                val hasInternet = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                val isNetworkValidated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                hasInternet && isNetworkValidated
            }
        } else {
            connectivityManager?.activeNetworkInfo?.isConnected ?: false
        }
    }
}