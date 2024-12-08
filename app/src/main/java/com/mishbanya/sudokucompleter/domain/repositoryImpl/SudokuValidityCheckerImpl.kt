package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker

class SudokuValidityCheckerImpl : SudokuValidityChecker {
    override fun isValidMove(field: Array<SudokuNode>, row: Int, col: Int, value: Int): Boolean {
        val currentSudokuField = SudokuField(field)
        for (i in 0..8) {
            if (currentSudokuField.getNode(row, i).value == value) {
                return false
            }
        }

        for (i in 0..8) {
            if (currentSudokuField.getNode(i, col).value == value) {
                return false
            }
        }

        val squareRowStart = (row / 3) * 3
        val squareColStart = (col / 3) * 3
        for (i in squareRowStart until squareRowStart + 3) {
            for (j in squareColStart until squareColStart + 3) {
                if (currentSudokuField.getNode(i, j).value == value) {
                    return false
                }
            }
        }

        return true
    }
}