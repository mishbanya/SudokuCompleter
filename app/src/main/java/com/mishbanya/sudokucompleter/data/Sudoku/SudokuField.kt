package com.mishbanya.sudokucompleter.data.Sudoku

import kotlinx.serialization.Serializable

@Serializable
data class SudokuField(
    val field: Array<Array<SudokuNode>>,
    val difficultyLevel: DifficultyLevel
) {
    fun getNode(row: Int, col: Int): SudokuNode {
        return field[row][col]
    }

    fun setNode(row: Int, col: Int, node: SudokuNode) {
        field[row][col] = node
    }

    fun deepCopy(): SudokuField {
        return SudokuField(
            field = field.map { row -> row.map { it.copy() }.toTypedArray() }.toTypedArray(),
            difficultyLevel = difficultyLevel
        )
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

