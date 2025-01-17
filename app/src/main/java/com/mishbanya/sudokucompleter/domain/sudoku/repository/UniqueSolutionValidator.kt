package com.mishbanya.sudokucompleter.domain.sudoku.repository

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode

interface UniqueSolutionValidator {
    fun hasUniqueSolution(field : Array<Array<SudokuNode>>): Boolean
}