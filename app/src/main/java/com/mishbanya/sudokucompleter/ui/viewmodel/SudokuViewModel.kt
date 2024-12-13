package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mishbanya.sudokucompleter.data.DifficultyLevel
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    private val sudokuGenerator: SudokuGenerator,
    private val sudokuValidityChecker: SudokuValidityChecker
): ViewModel() {
    private var _field : MutableStateFlow<SudokuField?> = MutableStateFlow(null)
    val field: StateFlow<SudokuField?>
        get() = _field.asStateFlow()

    fun generateSudoku(){
        _field.value = sudokuGenerator.generateInitialSudoku(difficulty = DifficultyLevel.EASY)
    }
}