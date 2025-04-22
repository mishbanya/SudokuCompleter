package com.mishbanya.sudokucompleter.domain.sudoku.solvers

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import kotlinx.coroutines.delay
import javax.inject.Inject

class BacktrackingSolverImpl @Inject constructor(
    private val validityChecker: SudokuValidityChecker
): BacktrackingSolver {

    override suspend fun solve(
        field: SudokuField,
        onUpdate: (SudokuField) -> Unit,
        cooldown: Int
    ): Boolean {
        return solveRecursively(field.deepCopy(), onUpdate, cooldown.toLong())
    }

    private suspend fun solveRecursively(
        field: SudokuField,
        onUpdate: (SudokuField) -> Unit,
        cooldown: Long,
        row: Int = 0,
        col: Int = 0
    ): Boolean {
        if (row == 9) return true

        val (nextRow, nextCol) = if (col == 8) row + 1 to 0 else row to col + 1

        if (field.getNode(row, col).value != null) {
            return solveRecursively(field, onUpdate, cooldown, nextRow, nextCol)
        }

        for (num in 1..9) {
            if (validityChecker.isValidMove(field.field, row, col, num)) {
                field.setNode(row, col, SudokuNode(num, SudokuNodeType.Filled))
                onUpdate(field.deepCopy())
                delay(cooldown)

                if (solveRecursively(field, onUpdate, cooldown, nextRow, nextCol)) {
                    return true
                }

                field.setNode(row, col, SudokuNode(null, SudokuNodeType.Unfilled))
                onUpdate(field.deepCopy())
                delay(cooldown)
            }
        }

        return false
    }
}