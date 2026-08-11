package com.example.rehive_pay.feature.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rehive_pay.R
import com.example.rehive_pay.ui.components.PrimaryButton

@Composable
fun AppUpdateScreen(
    onRestartApp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier.size(150.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Text(
            text = "App updated!",
            fontSize = 16.sp,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        PrimaryButton(
            text = "RESTART APP",
            onClick = onRestartApp,
            modifier = Modifier.fillMaxWidth(0.55f),
            fullWidth = false
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
