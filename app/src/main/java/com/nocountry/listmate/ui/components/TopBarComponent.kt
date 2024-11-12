package com.nocountry.listmate.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.theme.ListMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    title: Int,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)?,
    actions: (@Composable RowScope.() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = title),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                )
            }
        },
        modifier = modifier,
        navigationIcon = navigationIcon ?: {},
        actions = {
            if (actions != null) {
                actions()
            } else {
                Spacer(modifier = Modifier.width(64.dp)) // Adjust width to match possible action space
            }
        }
    )
}

@Preview
@Composable
fun TopBarComponentPreview() {
    ListMateTheme {
        TopBarComponent(
            title = R.string.app_name,
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrow_back),
                        contentDescription = "Arrow back"
                    )
                }
            },
            actions = null
        )
    }
}
