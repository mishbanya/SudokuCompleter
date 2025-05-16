package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.data.sudoku.deepCopy
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.ConstraintPropagationSolver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SudokuGeneratorImpl @Inject constructor(
    private val constraintPropagationSolver: ConstraintPropagationSolver,
    private val sudokuValidityChecker: SudokuValidityChecker
) : SudokuGenerator {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun generateSudoku(difficulty: DifficultyLevel): SudokuField =
        withContext(scope.coroutineContext) {
            var field: Array<Array<SudokuNode>>
            do {
                field = generateCompletedSudoku()
                removeNumbers(field, difficulty)
            } while (!
                constraintPropagationSolver.solve(
                    SudokuField(field.deepCopy(), DifficultyLevel.MEDIUM),
                    {},
                    0
                )
            )

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
                if (sudokuValidityChecker.isValidMove(field, row, col, value)) {
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
        val filledCells = difficulty.toCellsCount()
        val positions = (0..80).shuffled().take(81 - filledCells)
        for (position in positions) {
            val row = position / 9
            val col = position % 9
            field[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
        }
    }
}