package com.example.rehive_pay.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rehive_pay.base.local.DataStoreManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.rehive_pay.feature.app.AppUpdateScreen
import com.example.rehive_pay.feature.auth.ForgotPasswordScreen
import com.example.rehive_pay.feature.auth.LoginScreen
import com.example.rehive_pay.feature.auth.LoginViewModel
import com.example.rehive_pay.feature.auth.WelcomeScreen
import com.example.rehive_pay.feature.dashboard.DashboardScreen
import com.example.rehive_pay.feature.settings.SettingsScreen
import com.example.rehive_pay.feature.settings.*
import com.example.rehive_pay.feature.deposit.DepositFundsScreen
import com.example.rehive_pay.feature.transfer.AmountEntryScreen
import com.example.rehive_pay.feature.transfer.GlobalTransfersScreen
import com.example.rehive_pay.feature.profile.EditProfileScreen
import com.example.rehive_pay.feature.cards.AddCardScreen
import com.example.rehive_pay.feature.transfer.ScanQRScreen
import com.example.rehive_pay.feature.dashboard.TransactionDetailScreen

@Composable
fun AppNavGraph(startDestination: String = "splash") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("splash") {
            AppUpdateScreen(
                onRestartApp = {
                    navController.navigate("welcome") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        composable("welcome") {
            WelcomeScreen(
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        
        composable("login") { backStackEntry ->
            val loginViewModel: LoginViewModel = viewModel()
            
            // Retrieve the recovery email if passed back from ForgotPasswordScreen
            val recoveryEmail = backStackEntry.savedStateHandle.get<String>("recovery_email")
            
            LoginScreen(
                viewModel = loginViewModel,
                recoveryEmail = recoveryEmail,
                onBack = { navController.popBackStack() },
                onNavigateToForgotPassword = {
                    navController.navigate("forgot_password")
                },
                onLoginSuccess = {
                    // Save token to datastore
                    val dataStore = DataStoreManager(navController.context)
                    GlobalScope.launch {
                        dataStore.saveAuthToken("dummy_token")
                    }
                    // Navigate to dashboard/home after successful login
                    navController.navigate("dashboard") {
                        popUpTo("welcome") { inclusive = true }
                    }
                }
            )
        }
        
        composable("forgot_password") {
            ForgotPasswordScreen(
                onBack = { navController.popBackStack() },
                onSendRecovery = { email ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("recovery_email", email)
                    navController.popBackStack()
                }
            )
        }
        
        composable("dashboard") {
            DashboardScreen(
                onMenuClick = { navController.navigate("settings") },
                onSendClick = { navController.navigate("amount_entry") },
                onReceiveClick = { navController.navigate("deposit") },
                onScanClick = { navController.navigate("scan_qr") },
                onEditProfileClick = { navController.navigate("edit_profile") },
                onAddCardClick = { navController.navigate("add_card") },
                onTransactionClick = { tx ->
                    // Navigate to details passing basic string arguments
                    navController.navigate("transaction_detail/${tx.title}/${tx.amountUSDC}/${tx.type.name}")
                }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBack = { navController.popBackStack() },
                onNavigateToBankAccounts = { navController.navigate("bank_accounts") },
                onNavigateToCryptoAccounts = { navController.navigate("crypto_accounts") },
                onNavigateToDisplayCurrency = { navController.navigate("display_currency") },
                onNavigateToPrimaryCurrency = { navController.navigate("primary_currency") },
                onNavigateToNotifications = { navController.navigate("notifications") },
                onNavigateToMfa = { navController.navigate("mfa") },
                onNavigateToBiometrics = { navController.navigate("biometrics") },
                onNavigateToPassword = { navController.navigate("password") },
                onNavigateToDevices = { navController.navigate("devices") },
                onLogout = {
                    val dataStore = DataStoreManager(navController.context)
                    GlobalScope.launch {
                        dataStore.clear()
                        dataStore.setSplashShown(true)
                    }
                    navController.navigate("welcome") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable("bank_accounts") { 
            BankAccountsScreen(
                onBack = { navController.popBackStack() },
                onAddAccount = { navController.navigate("add_card") }
            ) 
        }
        composable("crypto_accounts") { 
            CryptoAccountsScreen(
                onBack = { navController.popBackStack() },
                onAddAccount = { navController.navigate("add_card") }
            ) 
        }
        composable("display_currency") { DisplayCurrencyScreen(onBack = { navController.popBackStack() }) }
        composable("primary_currency") { PrimaryCurrencyScreen(onBack = { navController.popBackStack() }) }
        composable("notifications") { NotificationsScreen(onBack = { navController.popBackStack() }) }
        composable("mfa") { MfaScreen(onBack = { navController.popBackStack() }) }
        composable("biometrics") { BiometricsScreen(onBack = { navController.popBackStack() }) }
        composable("password") { PasswordScreen(onBack = { navController.popBackStack() }) }
        composable("devices") { DevicesScreen(onBack = { navController.popBackStack() }) }

        composable("deposit") {
            DepositFundsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("amount_entry") {
            AmountEntryScreen(
                onBack = { navController.popBackStack() },
                onCameraClick = { navController.navigate("scan_qr") },
                onNext = { amount -> 
                    // Simulating a successful transaction by just popping back to dashboard
                    navController.popBackStack(route = "dashboard", inclusive = false)
                }
            )
        }

        composable("global_transfers") {
            GlobalTransfersScreen()
        }

        composable("edit_profile") {
            EditProfileScreen(onBack = { navController.popBackStack() })
        }

        composable("add_card") {
            AddCardScreen(onBack = { navController.popBackStack() })
        }

        composable("scan_qr") {
            ScanQRScreen(
                onBack = { navController.popBackStack() },
                onSimulateScan = { 
                    // Simulate scanning logic here. For now, pop back stack
                    navController.popBackStack()
                }
            )
        }

        composable("transaction_detail/{title}/{amount}/{type}") { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?: "Transaction"
            val amount = backStackEntry.arguments?.getString("amount") ?: "0.00 USDC"
            val type = backStackEntry.arguments?.getString("type") ?: "NEUTRAL"

            TransactionDetailScreen(
                title = title,
                amountUSDC = amount,
                amountUSD = "View details", // Mock static or passed
                type = type,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
