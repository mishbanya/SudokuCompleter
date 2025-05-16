package com.mishbanya.sudokucompleter.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mishbanya.sudokucompleter.ui.view.history.HistoryView
import com.mishbanya.sudokucompleter.ui.view.settings.SettingsView
import com.mishbanya.sudokucompleter.ui.view.sudoku.SudokuView
import com.mishbanya.sudokucompleter.ui.viewmodel.HistoryViewModel
import com.mishbanya.sudokucompleter.ui.viewmodel.SettingsViewModel
import com.mishbanya.sudokucompleter.ui.viewmodel.SudokuViewModel

@Composable
fun NavigationHost(navController: NavHostController, modifier: Modifier = Modifier) {

    val sudokuViewModel: SudokuViewModel = hiltViewModel()
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val historyViewModel: HistoryViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.SudokuView.route,
        modifier = modifier
    ) {
        composable(Screen.SudokuView.route) { SudokuView(sudokuViewModel) }
        composable(Screen.HistoryView.route) { HistoryView(historyViewModel) }
        composable(Screen.SettingsView.route) { SettingsView(settingsViewModel) }
    }
}