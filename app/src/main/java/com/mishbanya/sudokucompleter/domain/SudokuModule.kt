package com.mishbanya.sudokucompleter.domain

import android.util.Log
import com.mishbanya.sudokucompleter.domain.sudoku.repository.BacktrackingSolver
import com.mishbanya.sudokucompleter.domain.sudoku.repository.NodeSetter
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SolvedObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.sudoku.repository.UniqueSolutionValidator
import com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl.BacktrackingSolverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl.NodeSetterImpl
import com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl.SolvedObserverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl.SudokuGeneratorImpl
import com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl.SudokuValidityCheckerImpl
import com.mishbanya.sudokucompleter.domain.sudoku.repositoryImpl.UniqueSolutionValidatorImpl
import javax.inject.Singleton

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
    fun provideUniqueSolutionValidator(
        sudokuValidityChecker: SudokuValidityChecker
    ): UniqueSolutionValidator {
        Log.d("Hilt", "Creating UniqueSolutionValidator client instance")
        return UniqueSolutionValidatorImpl(sudokuValidityChecker)
    }

    @Provides
    @Singleton
    fun provideSudokuGenerator(
        sudokuValidityChecker: SudokuValidityChecker,
        uniqueSolutionValidator: UniqueSolutionValidator
    ): SudokuGenerator {
        Log.d("Hilt", "Creating SudokuGenerator client instance")
        return SudokuGeneratorImpl(sudokuValidityChecker, uniqueSolutionValidator)
    }

    @Provides
    @Singleton
    fun provideSolvedObserver() : SolvedObserver {
        Log.d("Hilt", "Creating SolvedObserver client instance")
        return SolvedObserverImpl()
    }
}