package com.mishbanya.sudokucompleter.domain

import android.util.Log
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.BacktrackingSolver
import com.mishbanya.sudokucompleter.domain.sudoku.NodeSetter
import com.mishbanya.sudokucompleter.domain.sudoku.SolvedObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mishbanya.sudokucompleter.domain.sudoku.generator.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.BacktrackingSolverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.NodeSetterImpl
import com.mishbanya.sudokucompleter.domain.sudoku.SolvedObserverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.generator.SudokuGeneratorImpl
import com.mishbanya.sudokucompleter.domain.sudoku.SudokuValidityCheckerImpl
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.ConstraintPropagationSolver
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.ConstraintPropagationSolverImpl
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.XAlgorithmSolver
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.XAlgorithmSolverImpl
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
    fun provideXSolver(

    ): XAlgorithmSolver {
        Log.d("Hilt", "Creating XAlgorithmSolver client instance")
        return XAlgorithmSolverImpl()
    }

    @Provides
    @Singleton
    fun providePropagationSolver(
        sudokuValidityChecker: SudokuValidityChecker
    ): ConstraintPropagationSolver {
        Log.d("Hilt", "Creating ConstraintPropagationSolver client instance")
        return ConstraintPropagationSolverImpl(sudokuValidityChecker)
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
    fun provideSudokuGenerator(
        xAlgorithmSolver: XAlgorithmSolver,
    ): SudokuGenerator {
        Log.d("Hilt", "Creating SudokuGenerator client instance")
        return SudokuGeneratorImpl(
            xAlgorithmSolver,
        )
    }

    @Provides
    @Singleton
    fun provideSolvedObserver() : SolvedObserver {
        Log.d("Hilt", "Creating SolvedObserver client instance")
        return SolvedObserverImpl()
    }
}