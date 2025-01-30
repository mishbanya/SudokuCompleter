package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField

interface BacktrackingSolver {
    suspend fun solve(field: SudokuField, onUpdate: (SudokuField) -> Unit, cooldown: Int) : Boolean
}