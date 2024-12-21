package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuField

interface BacktrackingSolverRepository {
    suspend fun solve(field: SudokuField, onUpdate: (SudokuField) -> Unit) : Boolean
}