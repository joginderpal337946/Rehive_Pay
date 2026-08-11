package com.example.rehive_pay.feature.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GlobalTransfersScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Easy Global Transfers",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Experience smooth international\ntransactions with no hidden barriers.",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Flags pattern matching the screenshot exactly
        Column(
            modifier = Modifier.fillMaxWidth().padding(end = 24.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Row 1
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FlagIcon("🇺🇸")
            }
            // Row 2
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FlagIcon("🇨🇦")
                FlagIcon("🇦🇷")
            }
            // Row 3
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                FlagIcon("🇦🇷")
                FlagIcon("🇰🇷")
                FlagIcon("🇵🇱")
            }
            // Row 4
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FlagIcon("🇿🇦")
                FlagIcon("🇹🇷")
                FlagIcon("🇺🇦")
                FlagIcon("🇬🇧")
            }
            // Row 5
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FlagIcon("🇮🇹")
                FlagIcon("🇰🇪")
                FlagIcon("🇳🇬")
                FlagIcon("🇿🇦")
            }
            // Row 6
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FlagIcon("🇧🇷")
                FlagIcon("🇪🇬")
                FlagIcon("🇮🇳")
                FlagIcon("🇪🇸")
            }
        }
    }
}

@Composable
fun FlagIcon(emoji: String) {
    Box(
        modifier = Modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(Color(0xFFFAFAFA)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = emoji,
            fontSize = 40.sp // Scales the emoji nicely inside the circle
        )
    }
}
