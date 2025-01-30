package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.SudokuNode
import kotlin.random.Random

interface GridFuzzer {
    fun fuzzGrid(grid: Array<Array<SudokuNode>>): Array<Array<SudokuNode>>
}