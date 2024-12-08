package com.mishbanya.sudokucompleter.domain

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import com.mishbanya.sudokucompleter.domain.repositoryImpl.SudokuGeneratorImpl
import com.mishbanya.sudokucompleter.domain.repositoryImpl.SudokuValidityCheckerImpl
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
    fun provideSudokuGenerator(
        sudokuValidityChecker: SudokuValidityChecker
    ): SudokuGenerator {
        Log.d("Hilt", "Creating SudokuGenerator client instance")
        return SudokuGeneratorImpl(sudokuValidityChecker)
    }
}