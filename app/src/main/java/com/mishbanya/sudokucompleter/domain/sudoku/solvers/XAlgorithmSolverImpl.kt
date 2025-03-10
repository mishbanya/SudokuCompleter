package com.mishbanya.sudokucompleter.domain.sudoku.solvers

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNodeType
import javax.inject.Inject
import kotlinx.coroutines.delay

class XAlgorithmSolverImpl @Inject constructor(): XAlgorithmSolver {
    override suspend fun solve(
        field: SudokuField,
        onUpdate: (SudokuField) -> Unit,
        cooldown: Int
    ): Boolean {
        val size = 9
        val grid = field.field.map { row -> row.map { it.value ?: 0 }.toIntArray() }.toTypedArray()

        val (X, Y) = exactCover(size)
        for (i in grid.indices) {
            for (j in grid[i].indices) {
                if (grid[i][j] != 0) {
                    select(X, Y, Triple(i, j, grid[i][j]))
                }
            }
        }

        return solve(X, Y, mutableListOf())?.let { solution ->
            solution.forEach { (r, c, n) ->
                field.field[r][c] = SudokuNode(n, SudokuNodeType.Filled)
                onUpdate(field)
                delay(cooldown.toLong())
            }
            true
        } ?: false
    }

    private fun exactCover(size: Int): Pair<MutableMap<Pair<String, Pair<Int, Int>>, MutableSet<Triple<Int, Int, Int>>>,
            MutableMap<Triple<Int, Int, Int>, List<Pair<String, Pair<Int, Int>>>>> {
        val X = mutableMapOf<Pair<String, Pair<Int, Int>>, MutableSet<Triple<Int, Int, Int>>>()
        val Y = mutableMapOf<Triple<Int, Int, Int>, List<Pair<String, Pair<Int, Int>>>>()

        for (r in 0 until size) {
            for (c in 0 until size) {
                for (n in 1..size) {
                    val b = (r / 3) * 3 + (c / 3)
                    val constraints = listOf(
                        "rc" to (r to c),
                        "rn" to (r to n),
                        "cn" to (c to n),
                        "bn" to (b to n)
                    )
                    Y[Triple(r, c, n)] = constraints
                    constraints.forEach { X.getOrPut(it) { mutableSetOf() }.add(Triple(r, c, n)) }
                }
            }
        }
        return X to Y
    }

    private fun solve(
        X: MutableMap<Pair<String, Pair<Int, Int>>, MutableSet<Triple<Int, Int, Int>>>,
        Y: MutableMap<Triple<Int, Int, Int>, List<Pair<String, Pair<Int, Int>>>>,
        solution: MutableList<Triple<Int, Int, Int>>
    ): List<Triple<Int, Int, Int>>? {
        if (X.isEmpty()) return solution.toList()
        val c = X.minByOrNull { it.value.size }?.key ?: return null

        for (r in X[c]?.toList() ?: emptyList()) {
            solution.add(r)
            val removedCols = select(X, Y, r)
            solve(X, Y, solution)?.let { return it }
            deselect(X, Y, r, removedCols)
            solution.remove(r)
        }
        return null
    }

    private fun select(
        X: MutableMap<Pair<String, Pair<Int, Int>>, MutableSet<Triple<Int, Int, Int>>>,
        Y: MutableMap<Triple<Int, Int, Int>, List<Pair<String, Pair<Int, Int>>>>,
        r: Triple<Int, Int, Int>
    ): List<MutableSet<Triple<Int, Int, Int>>> {
        val cols = mutableListOf<MutableSet<Triple<Int, Int, Int>>>()
        val keys = Y[r]?.toList() // Создаём копию, чтобы избежать ConcurrentModificationException
        if (keys != null) {
            for (j in keys) {
                for (i in X[j]?.toList() ?: emptyList()) {
                    for (k in Y[i]!!) {
                        if (k != j) {
                            X[k]?.remove(i)
                        }
                    }
                }
                X[j]?.let { cols.add(it) }
                X.remove(j)
            }
        }
        return cols
    }

    private fun deselect(
        X: MutableMap<Pair<String, Pair<Int, Int>>, MutableSet<Triple<Int, Int, Int>>>,
        Y: MutableMap<Triple<Int, Int, Int>, List<Pair<String, Pair<Int, Int>>>>,
        r: Triple<Int, Int, Int>,
        cols: List<MutableSet<Triple<Int, Int, Int>>>
    ) {
        Y[r]?.reversed()?.forEachIndexed { index, j ->
            X[j] = cols[index]
            X[j]?.forEach { i -> Y[i]?.forEach { k -> X[k]?.add(i) } }
        }
    }
}