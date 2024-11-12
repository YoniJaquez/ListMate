package com.nocountry.listmate.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nocountry.listmate.R
import com.nocountry.listmate.ui.navigation.Destinations

@Composable
fun BottomNavigationBar(navHostController: NavHostController) {

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val items = listOf(
        Destinations.HOME,
        Destinations.MY_TASKS,
        Destinations.PROFILE
    )

    val icons = listOf(
        ImageVector.vectorResource(id = R.drawable.home_icon),
        ImageVector.vectorResource(id = R.drawable.my_tasks_icon),
        ImageVector.vectorResource(id = R.drawable.profile_icon),
    )

    NavigationBar(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
        items.forEachIndexed { index, route ->
            val isSelected = currentDestination == route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navHostController.navigate(route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = icons[index],
                        contentDescription = null,
                        tint = if (isSelected) {
                            MaterialTheme.colorScheme.onTertiary
                        } else {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        }
                    )
                },
                label = {
                    Text(
                        stringResource(
                            id = when (index) {
                                0 -> R.string.home_bottom_nav_bar
                                1 -> R.string.my_tasks_bottom_nav_bar
                                else -> R.string.profile_bottom_nav_bar
                            }
                        ), color = MaterialTheme.colorScheme.onPrimaryContainer

                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    }
}