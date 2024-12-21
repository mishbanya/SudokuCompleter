package com.mishbanya.sudokucompleter.domain.sudoku.repository

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField

interface SolvedObserver {
    fun checkSolvedState(sudokuField: SudokuField): Boolean
}