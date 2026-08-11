package com.example.rehive_pay.base.network.interceptor

import com.example.rehive_pay.base.local.DataStoreManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val dataStoreManager: DataStoreManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        
        // Use runBlocking carefully, in a real app you might want to use a synchronous preference provider 
        // for OkHttp interceptors or pass the token explicitly.
        val token = runBlocking { dataStoreManager.authToken.firstOrNull() }
        
        if (!token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }
        
        return chain.proceed(requestBuilder.build())
    }
}
