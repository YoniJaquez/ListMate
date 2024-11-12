package com.nocountry.listmate.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.theme.ListMateTheme


@Composable
fun ButtonComponent(
    text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    icon: ImageVector?,
    textColor: Color,
    textStyle: TextStyle
) {
    Button(
        onClick = { onClick() },
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null){
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "icon",
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = stringResource(id = text),
                color = textColor,
                style = textStyle,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonComponentPreview(){
    ListMateTheme{
        ButtonComponent(
            text = R.string.app_name,
            onClick = { /*TODO*/ },
            backgroundColor = MaterialTheme.colorScheme.inversePrimary,
            icon = Icons.Default.Add,
            textColor = MaterialTheme.colorScheme.surfaceTint,
            textStyle = MaterialTheme.typography.bodyMedium
        )
    }
}
