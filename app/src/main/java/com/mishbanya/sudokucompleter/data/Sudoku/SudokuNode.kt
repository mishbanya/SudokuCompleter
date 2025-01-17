package com.mishbanya.sudokucompleter.data.Sudoku

import kotlinx.serialization.Serializable

@Serializable
data class SudokuNode(
    val value: Int?,
    val flag: SudokuNodeType
)
