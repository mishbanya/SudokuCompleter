package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType

class SolvedObserverImpl: SolvedObserver {
    override fun checkSolvedState(sudokuField: SudokuField): Boolean {
        var result = true
        sudokuField.field.forEach {
            it.forEach {
                result = result && (it.flag != SudokuNodeType.Unfilled)
            }
        }
        return result
    }
}