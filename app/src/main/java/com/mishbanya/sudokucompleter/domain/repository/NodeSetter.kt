package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuField

interface NodeSetter {
    fun setNode(sudokuField: SudokuField, row: Int, col: Int, value: Int?): SudokuField?
}