package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.repository.SolvedObserver

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