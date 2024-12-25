package com.mishbanya.sudokucompleter.domain.repository

import com.mishbanya.sudokucompleter.data.SudokuNode

interface UniqueSolutionValidator {
    fun hasUniqueSolution(field : Array<Array<SudokuNode>>): Boolean
}