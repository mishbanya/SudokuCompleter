package com.mishbanya.sudokucompleter.data

import kotlinx.serialization.Serializable

@Serializable
data class SudokuField(
    val field: Array<SudokuNode>
) {
    init {
        require(field.size == 81) { "Поле должно содержать ровно 81 элемент." }
    }

    fun getNode(row: Int, col: Int): SudokuNode {
        require(row in 0..8 && col in 0..8) { "Координаты должны быть в пределах 0..8." }
        return field[row * 9 + col]
    }

    fun setNode(row: Int, col: Int, node: SudokuNode) {
        require(row in 0..8 && col in 0..8) { "Координаты должны быть в пределах 0..8." }
        field[row * 9 + col] = node
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SudokuField) return false
        return field.contentEquals(other.field)
    }

    override fun hashCode(): Int {
        return field.contentHashCode()
    }
}
