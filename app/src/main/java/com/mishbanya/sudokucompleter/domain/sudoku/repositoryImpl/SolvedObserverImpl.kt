package com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SolvedObserver

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