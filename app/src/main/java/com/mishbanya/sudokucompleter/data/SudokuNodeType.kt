package com.mishbanya.sudokucompleter.data

import kotlinx.serialization.Serializable

@Serializable
enum class SudokuNodeType {
    Initial,
    Filled,
    Unfilled
}
