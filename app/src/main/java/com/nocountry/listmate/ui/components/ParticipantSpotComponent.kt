package com.nocountry.listmate.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ParticipantSpotComponent(name: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color.Gray),
        color = Color.White
    ) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                textAlign = TextAlign.Center
            )
            Icon(imageVector = Icons.Default.Clear, contentDescription = "Delete participant", modifier = Modifier.padding(5.dp))
        }

    }
}