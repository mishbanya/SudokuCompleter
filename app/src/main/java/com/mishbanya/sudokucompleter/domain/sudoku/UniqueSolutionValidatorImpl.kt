package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import kotlin.random.Random

class UniqueSolutionValidatorImpl(
    private val validityChecker: SudokuValidityChecker
): UniqueSolutionValidator {

    override fun hasUniqueSolution(grid: Array<Array<SudokuNode>>, random: Random): Boolean {
        var solutions = 0

        fun solve(row: Int = 0, col: Int = 0): Boolean {
            if (row == 9) {
                solutions++
                return solutions > 1
            }

            val nextRow = if (col == 8) row + 1 else row
            val nextCol = (col + 1) % 9

            if (grid[row][col].value != null) return solve(nextRow, nextCol)

            val numbers = (1..9).shuffled(random)
            for (num in numbers) {
                if (validityChecker.isValidMove(grid, row, col, num)) {
                    grid[row][col] = SudokuNode(num, SudokuNodeType.Initial)
                    if (solve(nextRow, nextCol)) return true
                    grid[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                }
            }
            return false
        }

        solve()
        return solutions == 1
    }

}