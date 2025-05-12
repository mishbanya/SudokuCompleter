package com.mishbanya.sudokucompleter.domain.history.repository

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField

interface HistoryWorker {
    fun saveSudoku(sudokuField: SudokuField): Boolean
    fun getHistorySize(): Int
    fun getHistory(from: Int, to: Int): List<SudokuField>
    fun clearHistory(): Boolean
}