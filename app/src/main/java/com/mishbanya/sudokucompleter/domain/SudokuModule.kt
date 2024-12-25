package com.mishbanya.sudokucompleter.domain

import android.util.Log
import com.mishbanya.sudokucompleter.domain.repository.BacktrackingSolverRepository
import com.mishbanya.sudokucompleter.domain.repository.NodeSetterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.repository.UniqueSolutionValidator
import com.mishbanya.sudokucompleter.domain.repositoryImpl.BacktrackingSolverRepositoryImpl
import com.mishbanya.sudokucompleter.domain.repositoryImpl.NodeSetterRepositoryImpl
import com.mishbanya.sudokucompleter.domain.repositoryImpl.SudokuGeneratorImpl
import com.mishbanya.sudokucompleter.domain.repositoryImpl.SudokuValidityCheckerImpl
import com.mishbanya.sudokucompleter.domain.repositoryImpl.UniqueSolutionValidatorImpl
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
    ): BacktrackingSolverRepository {
        Log.d("Hilt", "Creating BacktrackingSolver client instance")
        return BacktrackingSolverRepositoryImpl(sudokuValidityChecker)
    }

    @Provides
    @Singleton
    fun provideNodeSetter(
        sudokuValidityChecker: SudokuValidityChecker
    ): NodeSetterRepository {
        Log.d("Hilt", "Creating NodeSetter client instance")
        return NodeSetterRepositoryImpl(sudokuValidityChecker)
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
}