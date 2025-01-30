package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import kotlin.random.Random

class GridFuzzerImpl(
    private val random: Random
): GridFuzzer {

    override fun fuzzGrid(grid: Array<Array<SudokuNode>>): Array<Array<SudokuNode>> {

        // Перемешивание строк в пределах блока
        repeat(3) { block ->
            val rows = (0..2).shuffled(random)
            for (i in 0..2) {
                val row1 = block * 3 + rows[i]
                val row2 = block * 3 + i
                if (row1 != row2) swapRows(grid, row1, row2)
            }
        }

        // Перемешивание столбцов в пределах блока
        repeat(3) { block ->
            val cols = (0..2).shuffled(random)
            for (i in 0..2) {
                val col1 = block * 3 + cols[i]
                val col2 = block * 3 + i
                if (col1 != col2) swapColumns(grid, col1, col2)
            }
        }

        return grid
    }

    // Вспомогательные функции
    private fun swapRows(grid: Array<Array<SudokuNode>>, row1: Int, row2: Int) {
        val temp = grid[row1]
        grid[row1] = grid[row2]
        grid[row2] = temp
    }

    private fun swapColumns(grid: Array<Array<SudokuNode>>, col1: Int, col2: Int) {
        for (row in 0..8) {
            val temp = grid[row][col1]
            grid[row][col1] = grid[row][col2]
            grid[row][col2] = temp
        }
    }

}