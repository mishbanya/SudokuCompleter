package com.mishbanya.sudokucompleter.data.sudoku

import kotlinx.serialization.Serializable

@Serializable
data class SudokuNode(
    val value: Int?,
    val flag: SudokuNodeType
)
