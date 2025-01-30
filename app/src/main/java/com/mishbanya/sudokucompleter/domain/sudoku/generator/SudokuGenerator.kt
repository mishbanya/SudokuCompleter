package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField

interface SudokuGenerator {
    suspend fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField
}