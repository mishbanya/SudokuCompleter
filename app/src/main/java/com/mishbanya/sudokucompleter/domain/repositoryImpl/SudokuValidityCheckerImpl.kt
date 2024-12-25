package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker

class SudokuValidityCheckerImpl : SudokuValidityChecker {
    override fun isValidMove(field: Array<Array<SudokuNode>>, row: Int, col: Int, value: Int): Boolean {
        for (i in 0..8) {
            if (field[row][i].value == value || field[i][col].value == value ||
                field[row / 3 * 3 + i / 3][col / 3 * 3 + i % 3].value == value
            ) return false
        }
        return true
    }
}
