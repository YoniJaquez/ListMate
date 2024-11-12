package com.nocountry.listmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.nocountry.listmate.ui.navigation.ListMateApp
import com.nocountry.listmate.ui.theme.ListMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            ListMateTheme {
                ListMateApp()
            }
        }
    }


}