package com.example.rehive_pay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rehive_pay.base.local.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _startDestination = MutableStateFlow<String?>(null)
    val startDestination: StateFlow<String?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.isSplashShown.collect { isSplashShown ->
                if (!isSplashShown) {
                    _startDestination.value = "splash"
                } else {
                    dataStoreManager.authToken.collect { token ->
                        if (!token.isNullOrEmpty()) {
                            _startDestination.value = "dashboard"
                        } else {
                            _startDestination.value = "welcome"
                        }
                    }
                }
            }
        }
    }

    fun onSplashCompleted() {
        viewModelScope.launch {
            dataStoreManager.setSplashShown(true)
            // Once splash is completed, if they aren't logged in, they go to welcome.
            _startDestination.value = "welcome"
        }
    }
}
