package com.mishbanya.sudokucompleter.domain

import android.util.Log
import com.mishbanya.sudokucompleter.domain.sudoku.BacktrackingSolver
import com.mishbanya.sudokucompleter.domain.sudoku.NodeSetter
import com.mishbanya.sudokucompleter.domain.sudoku.SolvedObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mishbanya.sudokucompleter.domain.sudoku.generator.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.sudoku.BacktrackingSolverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.NodeSetterImpl
import com.mishbanya.sudokucompleter.domain.sudoku.SolvedObserverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.generator.SudokuGeneratorImpl
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityCheckerImpl
import com.mishbanya.sudokucompleter.domain.sudoku.UniqueSolutionValidator
import com.mishbanya.sudokucompleter.domain.sudoku.UniqueSolutionValidatorImpl
import com.mishbanya.sudokucompleter.domain.sudoku.generator.BaseGridGenerator
import com.mishbanya.sudokucompleter.domain.sudoku.generator.BaseGridGeneratorImpl
import com.mishbanya.sudokucompleter.domain.sudoku.generator.GridFuzzer
import com.mishbanya.sudokucompleter.domain.sudoku.generator.GridFuzzerImpl
import com.mishbanya.sudokucompleter.domain.sudoku.generator.GridValidDeleter
import com.mishbanya.sudokucompleter.domain.sudoku.generator.GridValidDeleterImpl
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object SudokuModule {

    @Provides
    @Singleton
    fun provideSudokuValidityChecker(): SudokuValidityChecker {
        Log.d("Hilt", "Creating SudokuValidityChecker client instance")
        return SudokuValidityCheckerImpl()
    }

    @Provides
    @Singleton
    fun provideBacktrakingSolver(
        sudokuValidityChecker: SudokuValidityChecker
    ): BacktrackingSolver {
        Log.d("Hilt", "Creating BacktrackingSolver client instance")
        return BacktrackingSolverImpl(sudokuValidityChecker)
    }

    @Provides
    @Singleton
    fun provideNodeSetter(
        sudokuValidityChecker: SudokuValidityChecker
    ): NodeSetter {
        Log.d("Hilt", "Creating NodeSetter client instance")
        return NodeSetterImpl(sudokuValidityChecker)
    }

    @Provides
    @Singleton
    fun provideBaseGridGenerator(
        random: Random
    ): BaseGridGenerator {
        Log.d("Hilt", "Creating BaseGridGenerator client instance")
        return BaseGridGeneratorImpl(random)
    }

    @Provides
    @Singleton
    fun provideGridFuzzer(
        random: Random
    ): GridFuzzer {
        Log.d("Hilt", "Creating GridFuzzer client instance")
        return GridFuzzerImpl(random)
    }

    @Provides
    @Singleton
    fun provideUniqueSolutionValidator(
        sudokuValidityChecker: SudokuValidityChecker
    ): UniqueSolutionValidator {
        Log.d("Hilt", "Creating SudokuValidityChecker client instance")
        return UniqueSolutionValidatorImpl(sudokuValidityChecker)
    }

    @Provides
    @Singleton
    fun provideGridValidDeleter(
        uniqueSolutionValidator: UniqueSolutionValidator,
        random: Random
    ): GridValidDeleter {
        Log.d("Hilt", "Creating GridValidDeleter client instance")
        return GridValidDeleterImpl(uniqueSolutionValidator, random)
    }

    @Provides
    @Singleton
    fun provideSudokuGenerator(
        sudokuValidityChecker: SudokuValidityChecker,
        baseGridGenerator: BaseGridGenerator,
        gridFuzzer: GridFuzzer,
        gridValidDeleter: GridValidDeleter
    ): SudokuGenerator {
        Log.d("Hilt", "Creating SudokuGenerator client instance")
        return SudokuGeneratorImpl(
            sudokuValidityChecker,
            baseGridGenerator,
            gridFuzzer,
            gridValidDeleter
        )
    }

    @Provides
    @Singleton
    fun provideSolvedObserver() : SolvedObserver {
        Log.d("Hilt", "Creating SolvedObserver client instance")
        return SolvedObserverImpl()
    }
}