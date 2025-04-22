package com.mishbanya.sudokucompleter.data.sudoku

import kotlinx.serialization.Serializable

@Serializable
enum class SudokuNodeType {
    Initial,
    Filled,
    FilledManually,
    Unfilled
}
