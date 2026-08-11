package com.example.rehive_pay.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rehive_pay.ui.components.CustomTextField
import com.example.rehive_pay.ui.components.PrimaryButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("Jane Doe") }
    var email by remember { mutableStateOf("jane.doe@example.com") }
    
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    val isEmailValid = email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    val isFormValid = name.isNotBlank() && isEmailValid

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.offset(x = (-12).dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                    contentDescription = "Back",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
            }
            
            Text(
                text = "Edit Profile",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = name,
            onValueChange = { name = it },
            label = "Full Name",
            placeholder = "Enter your full name"
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email Address",
            placeholder = "Enter your email",
            isError = email.isNotEmpty() && !isEmailValid,
            errorMessage = if (email.isNotEmpty() && !isEmailValid) "Invalid email address" else null
        )

        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(
            text = "Save Changes",
            onClick = {
                isLoading = true
                scope.launch {
                    delay(1500)
                    isLoading = false
                    onBack()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid,
            isLoading = isLoading
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}
