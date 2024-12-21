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
            DifficultyLevel.EASY -> 40
            DifficultyLevel.MEDIUM -> 30
            DifficultyLevel.HARD -> 20
        }

        val field = Array(9) {
            Array(9) { SudokuNode(null, SudokuNodeType.Unfilled) }
        }

        fun fillSudoku(row: Int = 0, col: Int = 0): Boolean {
            if (row == 9) return true
            if (col == 9) return fillSudoku(row + 1, 0)
            if (field[row][col].value != null) return fillSudoku(row, col + 1)

            val values = (1..9).shuffled()
            for (value in values) {
                if (sudokuValidityChecker.isValidMove(field, row, col, value)) {
                    field[row][col] = SudokuNode(value, SudokuNodeType.Initial)
                    if (fillSudoku(row, col + 1)) return true
                    field[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
                }
            }
            return false
        }

        fillSudoku()

        val positions = (0..80).shuffled().take(81 - filledCells)
        for (position in positions) {
            val row = position / 9
            val col = position % 9
            field[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)
        }

        return SudokuField(field,difficulty)
    }
}
