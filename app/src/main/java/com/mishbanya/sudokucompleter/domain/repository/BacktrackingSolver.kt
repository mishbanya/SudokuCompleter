package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuField

interface BacktrackingSolver {
    suspend fun solve(field: SudokuField, onUpdate: (SudokuField) -> Unit, cooldown: Int) : Boolean
}