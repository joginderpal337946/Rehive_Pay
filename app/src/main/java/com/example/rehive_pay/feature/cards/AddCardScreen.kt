package com.example.rehive_pay.feature.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rehive_pay.ui.components.CustomTextField
import com.example.rehive_pay.ui.components.PrimaryButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var cardNumber by remember { mutableStateOf("") }
    var cardName by remember { mutableStateOf("") }
    var expiry by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    
    var isLoading by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    
    // Validations
    val isCardValid = cardNumber.length == 16 && cardNumber.all { it.isDigit() }
    val isCvvValid = cvv.length == 3 && cvv.all { it.isDigit() }
    val isFormValid = isCardValid && cardName.isNotBlank() && expiry.isNotBlank() && isCvvValid

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
                text = "Add New Card",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        CustomTextField(
            value = cardNumber,
            onValueChange = { if (it.length <= 16 && it.all { char -> char.isDigit() }) cardNumber = it },
            label = "Card Number",
            placeholder = "0000 0000 0000 0000",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = cardNumber.isNotEmpty() && !isCardValid,
            errorMessage = if (cardNumber.isNotEmpty() && !isCardValid) "Card number must be 16 digits" else null
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        CustomTextField(
            value = cardName,
            onValueChange = { cardName = it },
            label = "Cardholder Name",
            placeholder = "Jane Doe"
        )

        Spacer(modifier = Modifier.height(24.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Box {
                    CustomTextField(
                        value = expiry,
                        onValueChange = { },
                        label = "Expiry Date",
                        placeholder = "MM/YY",
                        readOnly = true,
                    )
                    // Invisible clickable box over the text field
                    Box(modifier = Modifier.matchParentSize().clickable { showDatePicker = true })
                }
            }
            
            Column(modifier = Modifier.weight(1f)) {
                CustomTextField(
                    value = cvv,
                    onValueChange = { if (it.length <= 3 && it.all { char -> char.isDigit() }) cvv = it },
                    label = "CVV",
                    placeholder = "123",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = cvv.isNotEmpty() && !isCvvValid,
                    errorMessage = if (cvv.isNotEmpty() && !isCvvValid) "Must be 3 digits" else null
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        
        PrimaryButton(
            text = "Save Card",
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
        
        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("MM/yy", Locale.getDefault())
                            expiry = formatter.format(Date(millis))
                        }
                        showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
