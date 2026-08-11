package com.example.rehive_pay.base.connectivity

import kotlinx.coroutines.flow.Flow

interface ConnectivityProvider {
    val isConnected: Flow<Boolean>
    fun hasNetworkConnection(): Boolean
}
