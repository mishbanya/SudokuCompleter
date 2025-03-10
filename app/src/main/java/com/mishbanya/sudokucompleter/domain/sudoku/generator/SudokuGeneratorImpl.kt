package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.data.Sudoku.deepCopy
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.XAlgorithmSolver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SudokuGeneratorImpl @Inject constructor(
    private val xAlgorithmSolver: XAlgorithmSolver
) : SudokuGenerator {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField =
        withContext(scope.coroutineContext) {
            var field: Array<Array<SudokuNode>>
            do {
                field = generateCompletedSudoku()
                removeNumbers(field, difficulty)
            } while (!hasUniqueSolution(field.deepCopy()))

            return@withContext SudokuField(field, difficulty)
        }

    private fun generateCompletedSudoku(): Array<Array<SudokuNode>> {
        val field = Array(9) { Array(9) { SudokuNode(null, SudokuNodeType.Unfilled) } }
        fun fill(row: Int = 0, col: Int = 0): Boolean {
            if (row == 9) return true
            if (col == 9) return fill(row + 1, 0)
            if (field[row][col].value != null) return fill(row, col + 1)

            val values = (1..9).shuffled()
            for (value in values) {
                if (isValidMove(field, row, col, value)) {
                    field[row][col] = SudokuNode(value, SudokuNodeType.Initial)
                    if (fill(row, col + 1)) return true
                    field[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                }
            }
            return false
        }

        fill()
        return field
    }

    private fun removeNumbers(field: Array<Array<SudokuNode>>, difficulty: DifficultyLevel) {
        val filledCells = when (difficulty) {
            DifficultyLevel.EASY -> 40
            DifficultyLevel.MEDIUM -> 30
            DifficultyLevel.HARD -> 20
        }
        val positions = (0..80).shuffled().take(81 - filledCells)
        for (position in positions) {
            val row = position / 9
            val col = position % 9
            field[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
        }
    }

    private suspend fun hasUniqueSolution(field: Array<Array<SudokuNode>>): Boolean {
        return xAlgorithmSolver.solve(
            SudokuField(field, DifficultyLevel.MEDIUM),
            {},
            0
        )
    }

    private fun isValidMove(field: Array<Array<SudokuNode>>, row: Int, col: Int, num: Int): Boolean {
        return (0 until 9).none { field[row][it].value == num || field[it][col].value == num } &&
                (0 until 3).none { r -> (0 until 3).any { c -> field[row / 3 * 3 + r][col / 3 * 3 + c].value == num } }
    }
}