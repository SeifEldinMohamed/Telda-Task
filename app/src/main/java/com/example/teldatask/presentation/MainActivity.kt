package com.example.teldatask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.teldatask.presentation.navigation.AppNavHost
import com.example.teldatask.presentation.ui.theme.TeldaTaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TeldaTaskTheme {
                AppNavHost()
            }
        }
    }
}
