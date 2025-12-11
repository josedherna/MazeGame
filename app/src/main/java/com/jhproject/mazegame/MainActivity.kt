package com.jhproject.mazegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jhproject.mazegame.ui.navigation.AppNavigation
import com.jhproject.mazegame.ui.theme.MazeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MazeGameTheme {
                AppNavigation()
            }
        }
    }
}