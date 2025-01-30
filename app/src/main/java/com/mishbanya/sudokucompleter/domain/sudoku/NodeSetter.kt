package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField

interface NodeSetter {
    fun setNode(sudokuField: SudokuField, row: Int, col: Int, value: Int?): SudokuField?
}