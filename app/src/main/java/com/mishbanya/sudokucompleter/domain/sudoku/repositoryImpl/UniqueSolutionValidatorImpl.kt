package com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.sudoku.repository.UniqueSolutionValidator

class UniqueSolutionValidatorImpl(
    private val sudokuValidityChecker: SudokuValidityChecker
): UniqueSolutionValidator {
    override fun hasUniqueSolution(field: Array<Array<SudokuNode>>): Boolean {
        var solutions = 0

        val mutableBoard = field.map { row -> row.map { it.copy() }.toTypedArray() }.toTypedArray()

        fun solve(row: Int = 0, col: Int = 0): Boolean {
            if (row == 9) {
                solutions++
                return solutions == 1
            }

            val nextRow = if (col == 8) row + 1 else row
            val nextCol = if (col == 8) 0 else col + 1

            if (mutableBoard[row][col].value != null) {
                return solve(nextRow, nextCol)
            }

            for (num in 1..9) {
                if (sudokuValidityChecker.isValidMove(mutableBoard, row, col, num)) {
                    mutableBoard[row][col] = SudokuNode(num, SudokuNodeType.Filled)
                    if (solve(nextRow, nextCol)) {
                        mutableBoard[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                        return true
                    }
                    mutableBoard[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                }
            }

            return false
        }

        solve()
        return solutions == 1
    }

}