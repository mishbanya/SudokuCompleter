package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishbanya.sudokucompleter.data.DifficultyLevel
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.domain.repository.BacktrackingSolverRepository
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    private val sudokuGenerator: SudokuGenerator,
    private val backtrackingSolverRepository: BacktrackingSolverRepository
): ViewModel() {
    private var _field : MutableStateFlow<SudokuField?> = MutableStateFlow(null)
    val field: StateFlow<SudokuField?>
        get() = _field.asStateFlow()

    fun generateSudoku() {
        viewModelScope.launch {
            _field.emit(sudokuGenerator.generateInitialSudoku(difficulty = DifficultyLevel.EASY))
        }
    }

    fun solveSudoku() {
        viewModelScope.launch {
            _field.value?.let { initField ->
                backtrackingSolverRepository.solve(
                    field = initField,
                    onUpdate = { updatedField ->
                        viewModelScope.launch { _field.emit(updatedField) }
                    }
                )
            }
        }
    }
}