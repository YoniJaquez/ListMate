package com.nocountry.listmate.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.R

@Composable
fun Input(
    label: String,
    value: String,
    isPassword: Boolean = false,
    onValueChange: ((String) -> Unit)? = null
){
    var passwordVisible by rememberSaveable { mutableStateOf(!isPassword) }
    var keyboardOptions = if(isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default


    Spacer(modifier = Modifier.height(16.dp))
    TextField (
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange ={ onValueChange?.invoke(it) },
        shape = RoundedCornerShape(15.dp),
        label =  { Text(text = label ) },
        maxLines = 1,
        singleLine = true,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if(passwordVisible) R.drawable.eye_slash else R.drawable.eye

                    Icon(
                        painter = painterResource(id = icon),
                        modifier = Modifier.width(15.dp),
                        contentDescription = ""
                    )
                }
            }
        }
    )
}