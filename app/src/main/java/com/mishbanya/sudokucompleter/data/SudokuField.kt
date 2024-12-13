package com.mishbanya.sudokucompleter.data

import kotlinx.serialization.Serializable

@Serializable
data class SudokuField(
    val field: Array<Array<SudokuNode>>
) {
    init {
        require(field.size == 9 && field.all { it.size == 9 }) {
            "Поле должно быть размером 9x9."
        }
    }

    fun getNode(row: Int, col: Int): SudokuNode {
        require(row in 0..8 && col in 0..8) { "Координаты должны быть в пределах 0..8." }
        return field[row][col]
    }

    fun setNode(row: Int, col: Int, node: SudokuNode) {
        require(row in 0..8 && col in 0..8) { "Координаты должны быть в пределах 0..8." }
        field[row][col] = node
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SudokuField) return false
        return field.contentDeepEquals(other.field)
    }

    override fun hashCode(): Int {
        return field.contentDeepHashCode()
    }
}

