package com.example.rehive_pay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import com.example.rehive_pay.navigation.AppNavGraph
import com.example.rehive_pay.ui.theme.Rehive_PayTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rehive_pay.base.local.DataStoreManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val dataStoreManager = DataStoreManager(applicationContext)
        
        enableEdgeToEdge()
        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return MainViewModel(dataStoreManager) as T
                    }
                }
            )
            
            val startDestination by viewModel.startDestination.collectAsState()
            
            Rehive_PayTheme {
                if (startDestination != null) {
                    AppNavGraph(startDestination = startDestination!!)
                } else {
                    // Show a brief empty screen while resolving state
                    androidx.compose.foundation.layout.Box(
                        modifier = androidx.compose.ui.Modifier.fillMaxSize().background(Color.White)
                    )
                }
            }
        }
    }
}
