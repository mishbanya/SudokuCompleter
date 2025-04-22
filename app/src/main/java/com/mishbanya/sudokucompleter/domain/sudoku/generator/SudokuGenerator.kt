package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField

interface SudokuGenerator {
    suspend fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField
}