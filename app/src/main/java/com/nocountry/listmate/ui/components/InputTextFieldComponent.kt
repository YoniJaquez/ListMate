package com.nocountry.listmate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.R


@Composable
fun InputTextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: Int? = null,
    leadingIcon: ImageVector?,
    trailingIcon: @Composable () -> Unit,
    keyboardOptions: KeyboardOptions,
    placeholder: String?,
    supportingText: @Composable (() -> Unit)? = null,

) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            label?.let {
                Text(
                    text = stringResource(id = it),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        leadingIcon = if (leadingIcon != null) {
            {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Leading Icon Text Field",
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        } else null,
        trailingIcon = {trailingIcon()},
        keyboardOptions = keyboardOptions,
        supportingText = supportingText,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            if (placeholder != null) {
                Text(text = placeholder)
            }
        }


    )
}

@Preview(showBackground = true)
@Composable
fun InputTextFieldComponentPreview() {
    MaterialTheme {
        InputTextFieldComponent(
            value = "",
            onValueChange = {},
            label = R.string.app_name,
            leadingIcon = Icons.Default.Search,
            trailingIcon = { /*TODO*/ },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            placeholder = R.string.app_name.toString()
        )
    }
}


