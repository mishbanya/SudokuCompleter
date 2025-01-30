package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import kotlin.random.Random

class BaseGridGeneratorImpl(
    private val random: Random
): BaseGridGenerator {

    override fun generateBaseGrid(validityChecker: SudokuValidityChecker): Array<Array<SudokuNode>> {
        val grid = Array(9) { Array(9) { SudokuNode(null, SudokuNodeType.Unfilled) } }

        fun fillGrid(row: Int = 0, col: Int = 0): Boolean {
            if (row == 9) return true
            if (col == 9) return fillGrid(row + 1, 0)
            if (grid[row][col].value != null) return fillGrid(row, col + 1)

            val numbers = (1..9).shuffled(random)
            for (num in numbers) {
                if (validityChecker.isValidMove(grid, row, col, num)) {
                    grid[row][col] = SudokuNode(num, SudokuNodeType.Initial)
                    if (fillGrid(row, col + 1)) return true
                    grid[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                }
            }
            return false
        }

        fillGrid()
        return grid
    }
}