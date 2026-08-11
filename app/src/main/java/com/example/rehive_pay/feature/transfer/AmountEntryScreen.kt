package com.example.rehive_pay.feature.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CurrencyExchange
import androidx.compose.material.icons.filled.SwapVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rehive_pay.ui.components.PrimaryButton

@Composable
fun AmountEntryScreen(
    onBack: () -> Unit,
    onNext: (String) -> Unit,
    onCameraClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var amount by remember { mutableStateOf("0") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    val isNextEnabled = amount.toDoubleOrNull() ?: 0.0 > 0.0

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        // Top Bar
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
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
            
            IconButton(
                onClick = onCameraClick,
                modifier = Modifier.offset(x = 12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Scan",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Input Area
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "USDC",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                // Max button (dummy)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text("MAX", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(
                    text = amount,
                    fontSize = 64.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Swap currency button
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.SwapVert,
                        contentDescription = "Swap",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            val usdValue = (amount.toDoubleOrNull() ?: 0.0) * 0.99
            Text(
                text = String.format("%.2f USD", usdValue),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        // Custom Numpad
        Numpad(
            onNumberClick = { num ->
                if (amount == "0") {
                    amount = num
                } else if (amount.length < 10) {
                    amount += num
                }
            },
            onDotClick = {
                if (!amount.contains(".")) {
                    amount += "."
                }
            },
            onBackspaceClick = {
                if (amount.length > 1) {
                    amount = amount.dropLast(1)
                } else {
                    amount = "0"
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // From Account
        Text(
            text = "From Account",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(16.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF2775CA)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CurrencyExchange, // Using a generic icon
                    contentDescription = "USDC",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "USDC",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "USDC",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "2.32",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "2.32 USD",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PrimaryButton(
            text = "NEXT",
            onClick = {
                isLoading = true
                scope.launch {
                    delay(1500)
                    isLoading = false
                    onNext(amount)
                }
            },
            enabled = isNextEnabled,
            isLoading = isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun Numpad(
    onNumberClick: (String) -> Unit,
    onDotClick: () -> Unit,
    onBackspaceClick: () -> Unit
) {
    val buttons = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf(".", "0", "backspace")
    )

    Column(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        buttons.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { btn ->
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                when (btn) {
                                    "backspace" -> onBackspaceClick()
                                    "." -> onDotClick()
                                    else -> onNumberClick(btn)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (btn == "backspace") {
                            Icon(
                                imageVector = Icons.Default.Backspace,
                                contentDescription = "Backspace",
                                tint = Color.Black
                            )
                        } else {
                            Text(
                                text = btn,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}
