package com.mishbanya.sudokucompleter.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    data object SudokuView : Screen("sudoku", "Sudoku", Icons.Filled.Add)
    data object HistoryView : Screen("history", "History", Icons.Filled.History)
    data object SettingsView : Screen("settings", "Settings", Icons.Filled.Settings)

    companion object {
        val entries = listOf(SudokuView, HistoryView, SettingsView)
    }
}
