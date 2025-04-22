package com.mishbanya.sudokucompleter.domain.sudoku.solvers

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField

interface SudokuSolver {
    suspend fun solve(field: SudokuField, onUpdate: (SudokuField) -> Unit, cooldown: Int) : Boolean
}