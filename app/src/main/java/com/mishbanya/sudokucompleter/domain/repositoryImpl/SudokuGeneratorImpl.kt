package com.mishbanya.sudokucompleter.domain.repositoryImpl

import com.mishbanya.sudokucompleter.data.DifficultyLevel
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.data.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.repository.UniqueSolutionValidator
import javax.inject.Inject
import kotlin.random.Random

class SudokuGeneratorImpl @Inject constructor(
    private val sudokuValidityChecker: SudokuValidityChecker,
    private val uniqueSolutionValidator: UniqueSolutionValidator
) : SudokuGenerator {

    override suspend fun generateInitialSudoku(difficulty: DifficultyLevel): SudokuField {
        val filledCells = when (difficulty) {
            DifficultyLevel.EASY -> 40
            DifficultyLevel.MEDIUM -> 35
            DifficultyLevel.HARD -> 25
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

        val random = Random.Default
        var removed = 0

        while (removed < (81 - filledCells)) {
            val row = random.nextInt(0, 9)
            val col = random.nextInt(0, 9)

            if (field[row][col].value != null) {
                val backup = field[row][col]
                field[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)

                if (!uniqueSolutionValidator.hasUniqueSolution(field)) {
                    field[row][col] = backup
                } else {
                    removed++
                }
            }
        }

        return SudokuField(field,difficulty)
    }
}
