package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker

class SudokuValidityCheckerImpl : SudokuValidityChecker {
    override fun isValidMove(field: Array<Array<SudokuNode>>, row: Int, col: Int, value: Int): Boolean {
        // Проверка строки
        for (i in 0..8) {
            if (field[row][i].value == value) {
                return false
            }
        }

        // Проверка столбца
        for (i in 0..8) {
            if (field[i][col].value == value) {
                return false
            }
        }

        // Проверка 3x3 квадрата
        val squareRowStart = (row / 3) * 3
        val squareColStart = (col / 3) * 3
        for (i in squareRowStart until squareRowStart + 3) {
            for (j in squareColStart until squareColStart + 3) {
                if (field[i][j].value == value) {
                    return false
                }
            }
        }

        return true
    }
}
