package com.mishbanya.sudokucompleter.ui.navigation

import androidx.annotation.DrawableRes
import com.mishbanya.sudokucompleter.R

sealed class Screen(val route: String, val title: String, @DrawableRes val icon: Int) {
    data object SudokuView : Screen("sudoku", "Sudoku", R.drawable.sudoku)
    data object HistoryView : Screen("history", "History", R.drawable.recent)
    data object SettingsView : Screen("settings", "Settings", R.drawable.settings)

    companion object {
        val entries = listOf(SudokuView, HistoryView, SettingsView)
    }
}
