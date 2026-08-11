package com.example.rehive_pay.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onNavigateToBankAccounts: () -> Unit = {},
    onNavigateToCryptoAccounts: () -> Unit = {},
    onNavigateToDisplayCurrency: () -> Unit = {},
    onNavigateToPrimaryCurrency: () -> Unit = {},
    onNavigateToNotifications: () -> Unit = {},
    onNavigateToMfa: () -> Unit = {},
    onNavigateToBiometrics: () -> Unit = {},
    onNavigateToPassword: () -> Unit = {},
    onNavigateToDevices: () -> Unit = {},
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize().background(Color.White)) {
        var showDeleteDialog by remember { mutableStateOf(false) }
        var showLogoutDialog by remember { mutableStateOf(false) }
        var showDeactivateDialog by remember { mutableStateOf(false) }

        if (showDeactivateDialog) {
            AlertDialog(
                onDismissRequest = { showDeactivateDialog = false },
                title = { Text(text = "Deactivate Account") },
                text = { Text(text = "Are you sure you want to deactivate your account? You will be logged out.") },
                confirmButton = {
                    TextButton(onClick = { 
                        showDeactivateDialog = false 
                        onLogout()
                    }) {
                        Text("Deactivate", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeactivateDialog = false }) {
                        Text("Cancel", color = Color.Black)
                    }
                },
                containerColor = Color.White,
                titleContentColor = Color.Black,
                textContentColor = Color.Black
            )
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { Text(text = "Delete Account") },
                text = { Text(text = "Are you sure you want to request account deletion? This action cannot be undone.") },
                confirmButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDeleteDialog = false }) {
                        Text("Cancel", color = Color.Black)
                    }
                },
                containerColor = Color.White,
                titleContentColor = Color.Black,
                textContentColor = Color.Black
            )
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text(text = "Logout") },
                text = { Text(text = "Are you sure you want to log out?") },
                confirmButton = {
                    TextButton(onClick = { 
                        showLogoutDialog = false 
                        onLogout()
                    }) {
                        Text("Logout", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancel", color = Color.Black)
                    }
                },
                containerColor = Color.White,
                titleContentColor = Color.Black,
                textContentColor = Color.Black
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp , end = 16.dp, top = 30.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart).offset(x = (-12).dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                        contentDescription = "Back",
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                }
                
                Text(
                    text = "Settings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                item { Spacer(modifier = Modifier.height(16.dp)) }
                
                // External accounts section
                item { SectionTitle("External accounts") }
                item { SettingsItem("Bank accounts", onClick = onNavigateToBankAccounts) }
                item { SettingsItem("Crypto accounts", onClick = onNavigateToCryptoAccounts) }
                
                item { Spacer(modifier = Modifier.height(24.dp)) }
                
                // Preferences section
                item { SectionTitle("Preferences") }
                item { SettingsItem("Display currency", onClick = onNavigateToDisplayCurrency) }
                item { SettingsItem("Primary currency", onClick = onNavigateToPrimaryCurrency) }
                item { SettingsItem("Notifications", onClick = onNavigateToNotifications) }
                
                item { Spacer(modifier = Modifier.height(24.dp)) }
                
                // Security section
                item { SectionTitle("Security") }
                item { SettingsItem("Multi-factor authentication", onClick = onNavigateToMfa) }
                item { SettingsItem("Local authentication (biometrics)", onClick = onNavigateToBiometrics) }
                item { SettingsItem("Password", onClick = onNavigateToPassword) }
                item { SettingsItem("Devices", onClick = onNavigateToDevices) }
                item { SettingsItem("Request account deactivation", onClick = { showDeactivateDialog = true }) }
                item { SettingsItem("Request account deletion", onClick = { showDeleteDialog = true }) }
                item { SettingsItem("Logout", onClick = { showLogoutDialog = true }) }
                
                item { Spacer(modifier = Modifier.height(48.dp)) }
                
                // Footer
                item {
                    Text(
                        text = "App version: 7.8.14\nNative build version: 7.8.11",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF333333),
        modifier = Modifier.padding(top = 8.dp)
    )
}

@Composable
fun SettingsItem(title: String, onClick: () -> Unit = {}) {
    Text(
        text = title,
        fontSize = 15.sp,
        color = Color(0xFF444444),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(top = 10.dp)
    )
}
