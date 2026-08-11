package com.example.rehive_pay.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.CurrencyBitcoin
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rehive_pay.ui.components.CustomTextField
import com.example.rehive_pay.ui.components.PrimaryButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SettingsTopBar(title: String, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 30.dp)
    ) {
        IconButton(
            onClick = onBack, 
            modifier = Modifier.align(Alignment.CenterStart).offset(x = (-12).dp)
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBackIos, 
                contentDescription = "Back", 
                modifier = Modifier.size(20.dp), 
                tint = Color.Black
            )
        }
        Text(
            text = title, 
            fontSize = 18.sp, 
            fontWeight = FontWeight.Medium, 
            color = Color.Black, 
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun BankAccountsScreen(onBack: () -> Unit, onAddAccount: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Bank Accounts", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccountBalance, contentDescription = "Bank", tint = Color.Black)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Chase Bank", fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("**** 1234", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(text = "Add Bank Account", onClick = onAddAccount, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun CryptoAccountsScreen(onBack: () -> Unit, onAddAccount: () -> Unit = {}) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Crypto Accounts", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CurrencyBitcoin, contentDescription = "Crypto", tint = Color.Black)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text("Bitcoin Wallet", fontWeight = FontWeight.Bold, color = Color.Black)
                        Text("0.05 BTC", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            PrimaryButton(text = "Add Crypto Account", onClick = onAddAccount, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun DisplayCurrencyScreen(onBack: () -> Unit) {
    var selectedCurrency by remember { mutableStateOf("USD") }
    val currencies = listOf("USD - US Dollar", "EUR - Euro", "GBP - British Pound", "JPY - Japanese Yen")
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Display Currency", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            currencies.forEach { currency ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCurrency = currency }
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedCurrency == currency,
                        onClick = { selectedCurrency = currency }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = currency, fontSize = 16.sp, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun PrimaryCurrencyScreen(onBack: () -> Unit) {
    var selectedCurrency by remember { mutableStateOf("USD") }
    val currencies = listOf("USD", "EUR", "GBP", "USDC", "BTC")
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Primary Currency", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Your primary currency is used as the default for sending and receiving.", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(16.dp))

            currencies.forEach { currency ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectedCurrency = currency }
                        .padding(vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedCurrency == currency,
                        onClick = { selectedCurrency = currency }
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = currency, fontSize = 16.sp, color = Color.Black)
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(onBack: () -> Unit) {
    var pushEnabled by remember { mutableStateOf(true) }
    var emailEnabled by remember { mutableStateOf(false) }
    var smsEnabled by remember { mutableStateOf(true) }
    var marketingEnabled by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Notifications", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            NotificationToggleItem("Push Notifications", "Receive alerts on your device", pushEnabled) { pushEnabled = it }
            NotificationToggleItem("Email Notifications", "Receive updates via email", emailEnabled) { emailEnabled = it }
            NotificationToggleItem("SMS Notifications", "Receive text message alerts", smsEnabled) { smsEnabled = it }
            NotificationToggleItem("Marketing Emails", "Receive promotional offers", marketingEnabled) { marketingEnabled = it }
        }
    }
}

@Composable
fun NotificationToggleItem(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
            Text(subtitle, fontSize = 13.sp, color = Color.Gray)
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun MfaScreen(onBack: () -> Unit) {
    var mfaEnabled by remember { mutableStateOf(true) }
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Multi-factor Authentication", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Enable 2FA", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                    Text("Secure your account with an authenticator app.", fontSize = 13.sp, color = Color.Gray)
                }
                Switch(checked = mfaEnabled, onCheckedChange = { mfaEnabled = it })
            }
        }
    }
}

@Composable
fun BiometricsScreen(onBack: () -> Unit) {
    var biometricsEnabled by remember { mutableStateOf(false) }
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Local Authentication", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Enable Biometrics", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = Color.Black)
                    Text("Use Face ID or Fingerprint to unlock the app.", fontSize = 13.sp, color = Color.Gray)
                }
                Switch(checked = biometricsEnabled, onCheckedChange = { biometricsEnabled = it })
            }
        }
    }
}

@Composable
fun PasswordScreen(onBack: () -> Unit) {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var currentPasswordError by remember { mutableStateOf<String?>(null) }
    var newPasswordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    
    var currentPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Change Password", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            CustomTextField(
                value = currentPassword, 
                onValueChange = { 
                    currentPassword = it
                    currentPasswordError = null
                }, 
                label = "Current Password", 
                visualTransformation = if (currentPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = currentPasswordError != null,
                errorMessage = currentPasswordError,
                trailingIcon = {
                    IconButton(onClick = { currentPasswordVisible = !currentPasswordVisible }) {
                        Icon(
                            imageVector = if (currentPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (currentPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = newPassword, 
                onValueChange = { 
                    newPassword = it
                    newPasswordError = null
                }, 
                label = "New Password", 
                visualTransformation = if (newPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = newPasswordError != null,
                errorMessage = newPasswordError,
                trailingIcon = {
                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Icon(
                            imageVector = if (newPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (newPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = confirmPassword, 
                onValueChange = { 
                    confirmPassword = it
                    confirmPasswordError = null
                }, 
                label = "Confirm New Password", 
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = confirmPasswordError != null,
                errorMessage = confirmPasswordError,
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            PrimaryButton(
                text = "Update Password", 
                isLoading = isLoading,
                onClick = { 
                    var hasError = false
                    if (currentPassword.isEmpty()) {
                        currentPasswordError = "Current password is required"
                        hasError = true
                    }
                    if (newPassword.isEmpty()) {
                        newPasswordError = "New password is required"
                        hasError = true
                    } else if (newPassword.length < 6) {
                        newPasswordError = "Password must be at least 6 characters"
                        hasError = true
                    }
                    if (confirmPassword.isEmpty()) {
                        confirmPasswordError = "Confirm password is required"
                        hasError = true
                    } else if (newPassword != confirmPassword) {
                        confirmPasswordError = "Passwords do not match"
                        hasError = true
                    }
                    
                    if (hasError) return@PrimaryButton
                    
                    isLoading = true
                    scope.launch {
                        delay(1500)
                        isLoading = false
                        onBack()
                    }
                }, 
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun DevicesScreen(onBack: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        SettingsTopBar("Devices", onBack)
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text("Logged in devices", fontWeight = FontWeight.Bold, color = Color.Black)
            Spacer(modifier = Modifier.height(16.dp))
            
            DeviceItem("iPhone 13 Pro", "Active now • San Francisco, CA")
            Spacer(modifier = Modifier.height(12.dp))
            DeviceItem("MacBook Pro", "Last seen 2 days ago • San Francisco, CA")
        }
    }
}

@Composable
fun DeviceItem(name: String, status: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Devices, contentDescription = "Device", tint = Color.Black)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(name, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(status, color = Color.Gray, fontSize = 13.sp)
            }
        }
    }
}
