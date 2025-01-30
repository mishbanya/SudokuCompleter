package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker

interface BaseGridGenerator {
    fun generateBaseGrid(validityChecker: SudokuValidityChecker): Array<Array<SudokuNode>>
}