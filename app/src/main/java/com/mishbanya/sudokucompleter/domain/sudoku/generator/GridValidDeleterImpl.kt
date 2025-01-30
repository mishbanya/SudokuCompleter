package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode

class GridValidDeleterImpl: GridValidDeleter {
    override fun validlyDeleteCells(
        grid: Array<Array<SudokuNode>>,
        difficulty: DifficultyLevel
    ): Array<Array<SudokuNode>> {
        TODO("Not yet implemented")
    }
}