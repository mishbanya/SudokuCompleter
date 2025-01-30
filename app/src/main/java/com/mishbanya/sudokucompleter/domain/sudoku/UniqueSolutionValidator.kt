package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import kotlin.random.Random

interface UniqueSolutionValidator {
    fun hasUniqueSolution(grid: Array<Array<SudokuNode>>): Boolean
}