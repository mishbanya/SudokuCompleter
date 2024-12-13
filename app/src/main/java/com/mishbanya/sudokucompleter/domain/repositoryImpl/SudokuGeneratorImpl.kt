package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.DifficultyLevel
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.data.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import javax.inject.Inject

class SudokuGeneratorImpl @Inject constructor(
    private val sudokuValidityChecker: SudokuValidityChecker
) : SudokuGenerator {

    override fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField {
        val filledCells = when (difficulty) {
            DifficultyLevel.EASY -> 35
            DifficultyLevel.MEDIUM -> 30
            DifficultyLevel.HARD -> 25
        }

        // Инициализация пустого поля 9x9
        val field = Array(9) {
            Array(9) { SudokuNode(null, SudokuNodeType.Unfilled) }
        }

        var filledCount = 0
        while (filledCount < filledCells) {
            val row = (0..8).random()
            val col = (0..8).random()
            val value = (1..9).random()

            if (field[row][col].value == null && sudokuValidityChecker.isValidMove(field, row, col, value)) {
                field[row][col] = SudokuNode(value, SudokuNodeType.Initial)
                filledCount++
            }
        }

        return SudokuField(field)
    }
}
