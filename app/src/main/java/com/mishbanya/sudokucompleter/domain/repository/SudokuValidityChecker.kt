package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuNode

interface SudokuValidityChecker {
    fun isValidMove(field: Array<Array<SudokuNode>>, row: Int, col: Int, value: Int): Boolean
}