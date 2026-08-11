package com.example.rehive_pay.feature.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rehive_pay.ui.components.CustomTextField
import com.example.rehive_pay.ui.components.PrimaryButton
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    recoveryEmail: String? = null,
    onBack: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val loginError by viewModel.loginError.collectAsState()
    val isSuccess by viewModel.loginSuccess.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var passwordVisible by remember { mutableStateOf(false) }
    var showRecoveryMessage by remember(recoveryEmail) { mutableStateOf(recoveryEmail != null) }
    
    // Enable button only if both fields have content, as per Screenshot 2 (where it's grey/disabled when empty)
    val isButtonEnabled = email.isNotEmpty() && password.isNotEmpty() && !isLoading

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onLoginSuccess()
        }
    }

    LaunchedEffect(showRecoveryMessage) {
        if (showRecoveryMessage) {
            delay(5000)
            showRecoveryMessage = false
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 20.dp)
    ) {
        IconButton(
            onClick = onBack,
            modifier = Modifier.offset(x = (-12).dp).padding(top = 16.dp).align(Alignment.Start)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                contentDescription = "Back",
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Login to your account",
                fontSize = 16.sp,
                color = Color(0xFFAAAAAA)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        CustomTextField(
            value = email,
            onValueChange = viewModel::onEmailChange,
            label = "Email",
            placeholder = "e.g. hello@gmail.com",
            isError = emailError != null,
            errorMessage = emailError,
            onFocusChanged = { focusState ->
                if (!focusState.isFocused) {
                    viewModel.validateEmailFormat()
                }
            }
        )

        Spacer(modifier = Modifier.height(10.dp))

        CustomTextField(
            value = password,
            onValueChange = viewModel::onPasswordChange,
            label = "Password",
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = Color(0xFFAAAAAA))
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 4.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Must be more than 8 characters",
                fontSize = 12.sp,
                color = Color(0xFFAAAAAA)
            )
            Text(
                text = "${password.length} / 8",
                fontSize = 12.sp,
                color = Color(0xFFAAAAAA)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Forgot password?",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.End)
                .clickable { onNavigateToForgotPassword() }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Display Global Login Error (Screenshot 3)
        if (loginError != null) {
            Text(
                text = loginError!!,
                color = Color(0xFFFF5252),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )
        }

        PrimaryButton(
            text = "LOGIN",
            onClick = viewModel::onLoginClick,
            enabled = isButtonEnabled,
            isLoading = isLoading
        )
        if (showRecoveryMessage && recoveryEmail != null) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Instructions on how to reset\nyour password have been sent to\n$recoveryEmail",
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}
