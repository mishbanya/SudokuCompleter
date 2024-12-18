package com.mishbanya.sudokucompleter.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mishbanya.sudokucompleter.ui.navigation.BottomNavigationBar
import com.mishbanya.sudokucompleter.ui.navigation.NavigationHost
import com.mishbanya.sudokucompleter.ui.theme.SudokuCompleterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SudokuCompleterTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar()
                    }
                ) { innerPadding ->
                    NavigationHost(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
