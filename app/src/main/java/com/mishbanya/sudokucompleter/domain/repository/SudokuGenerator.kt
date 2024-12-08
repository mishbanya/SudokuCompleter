package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.DifficultyLevel
import com.mishbanya.sudokucompleter.data.SudokuField

interface SudokuGenerator {
    fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField
}