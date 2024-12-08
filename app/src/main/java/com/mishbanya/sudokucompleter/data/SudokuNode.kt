package com.mishbanya.sudokucompleter.data

import kotlinx.serialization.Serializable

@Serializable
data class SudokuNode(
    val value: Int?,
    val flag: SudokuNodeType
) {
    init {
        require(value in 0..9) { "Значение должно быть между 0 и 9." }
    }
}
