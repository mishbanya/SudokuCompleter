package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SudokuGeneratorImpl @Inject constructor(
    private val validityChecker: SudokuValidityChecker
) : SudokuGenerator {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun generateInitialSudoku(
        difficulty: DifficultyLevel
    ): SudokuField = withContext(scope.coroutineContext) {
        TODO()
    }
}