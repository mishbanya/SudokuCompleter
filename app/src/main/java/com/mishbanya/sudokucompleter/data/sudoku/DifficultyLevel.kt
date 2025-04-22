package com.mishbanya.sudokucompleter.data.sudoku

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mishbanya.sudokucompleter.R

enum class DifficultyLevel {
    EASY,
    MEDIUM,
    HARD;

    fun toIndicator(): String {
        return when (this) {
            EASY -> "E"
            MEDIUM -> "M"
            HARD -> "H"
        }
    }
    fun toCellsCount(): Int {
        return when (this) {
            EASY -> 40
            MEDIUM -> 35
            HARD -> 30
        }
    }
    @Composable
    fun toStringRes(): String {
        return when (this) {
            EASY -> stringResource(R.string.difficulty_easy)
            MEDIUM -> stringResource(R.string.difficulty_normal)
            HARD -> stringResource(R.string.difficulty_hard)
        }
    }
}