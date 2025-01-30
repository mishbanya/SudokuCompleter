package com.mishbanya.sudokucompleter.domain.sudoku.generator

import com.mishbanya.sudokucompleter.data.Sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.Sudoku.SudokuField
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SudokuGeneratorImpl @Inject constructor(
    private val validityChecker: SudokuValidityChecker,
    private val baseGridGenerator: BaseGridGenerator,
    private val gridFuzzer: GridFuzzer,
    private val gridValidDeleter: GridValidDeleter
) : SudokuGenerator {
    private val scope = CoroutineScope(Dispatchers.IO)

    override suspend fun generateInitialSudoku(
        difficulty: DifficultyLevel
    ): SudokuField = withContext(scope.coroutineContext) {
        // 1. Генерация валидной заполненной базовой сетки
        val baseGrid = baseGridGenerator.generateBaseGrid(validityChecker)

        // 2. Трансформация базовой сетки
        val transformedGrid = gridFuzzer.fuzzGrid(baseGrid)

        // 3. Удаление клеток с сохранением уникальности решения
        val puzzleGrid = gridValidDeleter.validlyDeleteCells(transformedGrid, difficulty)

        SudokuField(puzzleGrid, difficulty)
    }
}