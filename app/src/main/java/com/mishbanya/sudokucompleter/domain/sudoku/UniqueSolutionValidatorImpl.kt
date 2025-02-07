package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.data.Sudoku.deepCopy
import javax.inject.Inject
import kotlin.random.Random

class UniqueSolutionValidatorImpl @Inject constructor(
    private val validityChecker: SudokuValidityChecker,
    private val random: Random
) : UniqueSolutionValidator {

    override fun hasUniqueSolution(grid: Array<Array<SudokuNode>>): Boolean {
        var solutions = 0
        // Создаем глубокую копию сетки для работы
        val tempGrid = grid.deepCopy()

        fun solve(row: Int = 0, col: Int = 0): Boolean {
            if (solutions > 1) return true // Прекращаем поиск при втором решении

            if (row == 9) {
                solutions++
                // Продолжаем поиск только если нашли первое решение
                return solutions == 1
            }

            val nextRow = if (col == 8) row + 1 else row
            val nextCol = (col + 1) % 9

            if (tempGrid[row][col].value != null) {
                return solve(nextRow, nextCol)
            }

            val numbers = (1..9).shuffled(random)
            for (num in numbers) {
                if (validityChecker.isValidMove(tempGrid, row, col, num)) {
                    tempGrid[row][col] = SudokuNode(num, SudokuNodeType.Filled)
                    if (solve(nextRow, nextCol)) continue
                    tempGrid[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                }
            }
            return solutions > 1
        }

        solve()
        return solutions == 1
    }
}