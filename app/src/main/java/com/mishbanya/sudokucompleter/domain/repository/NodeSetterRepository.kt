package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuField

interface NodeSetterRepository {
    fun setNode(sudokuField: SudokuField, row: Int, col: Int, value: Int?): SudokuField?
}