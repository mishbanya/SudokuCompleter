package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuField

interface SolvedObserver {
    fun checkSolvedState(sudokuField: SudokuField): Boolean
}