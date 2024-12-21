package com.mishbanya.sudokucompleter.domain.sudoku.repository

import com.mishbanya.sudokucompleter.data.sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField

interface SudokuGenerator {
    fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField
}