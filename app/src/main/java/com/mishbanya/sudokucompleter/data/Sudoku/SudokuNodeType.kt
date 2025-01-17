package com.mishbanya.sudokucompleter.data.Sudoku

import kotlinx.serialization.Serializable

@Serializable
enum class SudokuNodeType {
    Initial,
    Filled,
    FilledManually,
    Unfilled
}
