package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode

interface SudokuValidityChecker {
    fun isValidMove(field: Array<Array<SudokuNode>>, row: Int, col: Int, value: Int): Boolean
}