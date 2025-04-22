package com.mishbanya.sudokucompleter.domain.sudoku.solvers

import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import kotlinx.coroutines.delay
import javax.inject.Inject

class ConstraintPropagationSolverImpl @Inject constructor(
    private val validityChecker: SudokuValidityChecker
) : ConstraintPropagationSolver {

    override suspend fun solve(
        field: SudokuField,
        onUpdate: (SudokuField) -> Unit,
        cooldown: Int
    ): Boolean {
        val copy = field.deepCopy()
        val possibilities = Array(9) { row ->
            Array(9) { col ->
                if (copy.getNode(row, col).value != null) mutableSetOf()
                else (1..9).filter { num -> validityChecker.isValidMove(copy.field, row, col, num) }.toMutableSet()
            }
        }

        while (true) {
            var updated = false

            if (applyNakedSingles(copy, possibilities, onUpdate, cooldown)) {
                updated = true
                continue
            }

            if (applyHiddenSingles(copy, possibilities, onUpdate, cooldown)) {
                updated = true
                continue
            }

            if (applyNakedPairs(possibilities)) {
                updated = true
                continue
            }

            if (applyPointingPairs(possibilities)) {
                updated = true
                continue
            }

            if (!updated) break
        }

        return copy.field.all { row -> row.all { it.value != null } }
    }

    private suspend fun applyNakedSingles(
        field: SudokuField,
        possibilities: Array<Array<MutableSet<Int>>>,
        onUpdate: (SudokuField) -> Unit,
        cooldown: Int
    ): Boolean {
        for (row in 0..8) {
            for (col in 0..8) {
                if (field.getNode(row, col).value == null && possibilities[row][col].size == 1) {
                    val value = possibilities[row][col].first()
                    field.setNode(row, col, SudokuNode(value, SudokuNodeType.Filled))
                    onUpdate(field.deepCopy())
                    delay(cooldown.toLong())
                    updatePossibilities(possibilities, row, col, value)
                    return true
                }
            }
        }
        return false
    }

    private suspend fun applyHiddenSingles(
        field: SudokuField,
        possibilities: Array<Array<MutableSet<Int>>>,
        onUpdate: (SudokuField) -> Unit,
        cooldown: Int
    ): Boolean {
        for (num in 1..9) {
            // Rows
            for (row in 0..8) {
                val cols = (0..8).filter { col -> num in possibilities[row][col] }
                if (cols.size == 1) {
                    val col = cols.first()
                    if (field.getNode(row, col).value == null) {
                        field.setNode(row, col, SudokuNode(num, SudokuNodeType.Filled))
                        onUpdate(field.deepCopy())
                        delay(cooldown.toLong())
                        updatePossibilities(possibilities, row, col, num)
                        return true
                    }
                }
            }

            // Columns
            for (col in 0..8) {
                val rows = (0..8).filter { row -> num in possibilities[row][col] }
                if (rows.size == 1) {
                    val row = rows.first()
                    if (field.getNode(row, col).value == null) {
                        field.setNode(row, col, SudokuNode(num, SudokuNodeType.Filled))
                        onUpdate(field.deepCopy())
                        delay(cooldown.toLong())
                        updatePossibilities(possibilities, row, col, num)
                        return true
                    }
                }
            }

            // Boxes
            for (boxRow in 0..2) {
                for (boxCol in 0..2) {
                    val cells = mutableListOf<Pair<Int, Int>>()
                    for (r in 0..2) {
                        for (c in 0..2) {
                            val row = boxRow * 3 + r
                            val col = boxCol * 3 + c
                            if (num in possibilities[row][col]) {
                                cells.add(row to col)
                            }
                        }
                    }
                    if (cells.size == 1) {
                        val (row, col) = cells.first()
                        if (field.getNode(row, col).value == null) {
                            field.setNode(row, col, SudokuNode(num, SudokuNodeType.Filled))
                            onUpdate(field.deepCopy())
                            delay(cooldown.toLong())
                            updatePossibilities(possibilities, row, col, num)
                            return true
                        }
                    }
                }
            }
        }

        return false
    }

    private fun applyNakedPairs(possibilities: Array<Array<MutableSet<Int>>>): Boolean {
        var updated = false

        // Rows
        for (row in 0..8) {
            val pairs = (0..8).map { it to possibilities[row][it] }.filter { it.second.size == 2 }
            val pairGroups = pairs.groupBy { it.second }
            for ((pairSet, cells) in pairGroups) {
                if (cells.size == 2) {
                    for (i in 0..8) {
                        if (i !in cells.map { it.first }) {
                            updated = updated or possibilities[row][i].removeAll(pairSet)
                        }
                    }
                }
            }
        }

        // Columns
        for (col in 0..8) {
            val pairs = (0..8).map { it to possibilities[it][col] }.filter { it.second.size == 2 }
            val pairGroups = pairs.groupBy { it.second }
            for ((pairSet, cells) in pairGroups) {
                if (cells.size == 2) {
                    for (i in 0..8) {
                        if (i !in cells.map { it.first }) {
                            updated = updated or possibilities[i][col].removeAll(pairSet)
                        }
                    }
                }
            }
        }

        return updated
    }

    private fun applyPointingPairs(possibilities: Array<Array<MutableSet<Int>>>): Boolean {
        var updated = false

        for (num in 1..9) {
            for (boxRow in 0..2) {
                for (boxCol in 0..2) {
                    val cells = mutableListOf<Pair<Int, Int>>()
                    for (r in 0..2) {
                        for (c in 0..2) {
                            val row = boxRow * 3 + r
                            val col = boxCol * 3 + c
                            if (possibilities[row][col].contains(num)) {
                                cells.add(row to col)
                            }
                        }
                    }

                    val uniqueRows = cells.map { it.first }.distinct()
                    val uniqueCols = cells.map { it.second }.distinct()

                    if (uniqueRows.size == 1) {
                        val row = uniqueRows.first()
                        for (col in 0..8) {
                            if (col !in cells.map { it.second }) {
                                updated = updated or possibilities[row][col].remove(num)
                            }
                        }
                    }

                    if (uniqueCols.size == 1) {
                        val col = uniqueCols.first()
                        for (row in 0..8) {
                            if (row !in cells.map { it.first }) {
                                updated = updated or possibilities[row][col].remove(num)
                            }
                        }
                    }
                }
            }
        }

        return updated
    }

    private fun updatePossibilities(
        possibilities: Array<Array<MutableSet<Int>>>,
        row: Int,
        col: Int,
        value: Int
    ) {
        for (i in 0..8) {
            possibilities[row][i].remove(value)
            possibilities[i][col].remove(value)
        }

        val startRow = (row / 3) * 3
        val startCol = (col / 3) * 3
        for (r in startRow until startRow + 3) {
            for (c in startCol until startCol + 3) {
                possibilities[r][c].remove(value)
            }
        }
        possibilities[row][col].clear()
    }
}
