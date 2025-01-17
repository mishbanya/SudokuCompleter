package com.mishbanya.sudokucompleter.domain.sudoku.repository

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField

interface SudokuGenerator {
    fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField
}