package com.mishbanya.sudokucompleter.domain.sudoku

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType

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