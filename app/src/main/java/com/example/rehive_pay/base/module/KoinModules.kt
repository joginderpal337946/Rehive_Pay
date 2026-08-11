package com.example.rehive_pay.base.module

import com.example.rehive_pay.MainViewModel
import com.example.rehive_pay.base.connectivity.ConnectivityProvider
import com.example.rehive_pay.base.connectivity.ConnectivityProviderImpl
import com.example.rehive_pay.base.local.DataStoreManager
import com.example.rehive_pay.base.network.interceptor.BasicAuthInterceptor
import com.example.rehive_pay.base.network.interceptor.MockApiInterceptor
import com.example.rehive_pay.base.permission.PermissionHandler
import com.example.rehive_pay.base.permission.PermissionHandlerImpl
import com.example.rehive_pay.data.api.ApiHelper
import com.example.rehive_pay.data.api.ApiHelperImpl
import com.example.rehive_pay.data.api.ApiService
import com.example.rehive_pay.data.api.Constants
import com.example.rehive_pay.feature.settings.SettingsViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { DataStoreManager(androidContext()) }
    single<ConnectivityProvider> { ConnectivityProviderImpl(androidContext()) }
    single<PermissionHandler> { PermissionHandlerImpl(androidContext()) }
    viewModel { MainViewModel(get()) }
    viewModel { SettingsViewModel(get()) }
}

val networkModule = module {
    single { BasicAuthInterceptor(get()) }
    
    single { MockApiInterceptor() }
    
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(get<BasicAuthInterceptor>())
            .addInterceptor(get<MockApiInterceptor>())
            .connectTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }
    
    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    single { get<Retrofit>().create(ApiService::class.java) }
    
    single<ApiHelper> { ApiHelperImpl(get()) }
}
