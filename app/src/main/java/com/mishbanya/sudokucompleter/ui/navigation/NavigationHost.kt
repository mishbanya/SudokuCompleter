package com.mishbanya.sudokucompleter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mishbanya.sudokucompleter.ui.view.SudokuView

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = Screen.SudokuView.route,
        modifier = modifier
    ) {
        composable(Screen.SudokuView.route) { SudokuView() }
//        composable(Screen.HistoryView.route) { HistoryView() }
//        composable(Screen.SettingsView.route) { SettingsView() }
    }
}