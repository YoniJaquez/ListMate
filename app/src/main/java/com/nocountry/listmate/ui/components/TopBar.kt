package com.nocountry.listmate.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview
fun TopBarPreview(){
    Column {

        TopBar("Login")
        TopBar("Sign Up")
    }

}
@Composable

fun TopBar(
    titulo: String
){
    Column(
        modifier = Modifier.height(90.dp)
            .fillMaxWidth()
            .background(color = Color(0xFF98ccf9), shape = RoundedCornerShape(0.dp))
            .padding(10.dp, 5.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titulo,
            color = Color(0xFF000000),
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 25.sp
            )
        )
    }

}
