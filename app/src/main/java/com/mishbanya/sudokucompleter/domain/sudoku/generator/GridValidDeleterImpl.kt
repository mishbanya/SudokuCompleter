package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.sudoku.UniqueSolutionValidator
import kotlin.random.Random

class GridValidDeleterImpl(
    private val uniqueSolutionValidator: UniqueSolutionValidator,
    private val random: Random
): GridValidDeleter {

    override fun validlyDeleteCells(
        grid: Array<Array<SudokuNode>>,
        difficulty: DifficultyLevel
    ): Array<Array<SudokuNode>> {
        val cellsToRemove = when (difficulty) {
            DifficultyLevel.EASY -> 45
            DifficultyLevel.MEDIUM -> 50
            DifficultyLevel.HARD -> 55
        }
        val removalOrder = (0..80).shuffled(random).toMutableList()
        var removed = 0

        while (removed < cellsToRemove && removalOrder.isNotEmpty()) {
            val position = removalOrder.removeAt(0)
            val row = position / 9
            val col = position % 9

            if (grid[row][col].value == null) continue

            val backup = grid[row][col]
            grid[row][col] = SudokuNode(null, SudokuNodeType.Unfilled)

            if (uniqueSolutionValidator.hasUniqueSolution(grid, random)) {
                removed++
            } else {
                grid[row][col] = backup
            }
        }

        return grid
    }
}