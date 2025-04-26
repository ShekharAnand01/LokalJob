package com.shekhar.lokal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.shekhar.lokal.navigation.MainNavigation
import com.shekhar.lokal.ui.theme.LokalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LokalTheme {
                MainNavigation()
            }
        }
    }
}
