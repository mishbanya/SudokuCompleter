package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.CONSTANTS.UPDATE_COOLDOWN
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.data.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.repository.BacktrackingSolverRepository
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import kotlinx.coroutines.delay
import javax.inject.Inject

class BacktrackingSolverRepositoryImpl @Inject constructor(
    private val validityChecker: SudokuValidityChecker
): BacktrackingSolverRepository {

    override suspend fun solve(field: SudokuField, onUpdate: (SudokuField) -> Unit): Boolean {
            return solveRecursively(field.deepCopy(), onUpdate)
    }

    private suspend fun solveRecursively(
        field: SudokuField,
        onUpdate: (SudokuField) -> Unit,
        row: Int = 0,
        col: Int = 0
    ): Boolean {
        if (row == 9) return true

        val (nextRow, nextCol) = if (col == 8) row + 1 to 0 else row to col + 1

        if (field.getNode(row, col).value != null) {
            return solveRecursively(field, onUpdate, nextRow, nextCol)
        }

        for (num in 1..9) {
            if (validityChecker.isValidMove(field.field, row, col, num)) {
                field.setNode(row, col, SudokuNode(num, SudokuNodeType.Filled))
                onUpdate(field.deepCopy())
                delay(UPDATE_COOLDOWN)

                if (solveRecursively(field, onUpdate, nextRow, nextCol)) {
                    return true
                }

                field.setNode(row, col, SudokuNode(null, SudokuNodeType.Unfilled))
                onUpdate(field.deepCopy())
                delay(UPDATE_COOLDOWN)
            }
        }

        return false
    }

}