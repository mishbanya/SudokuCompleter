package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishbanya.sudokucompleter.data.DifficultyLevel
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.data.SudokuNodeType
import com.mishbanya.sudokucompleter.domain.repository.BacktrackingSolverRepository
import com.mishbanya.sudokucompleter.domain.repository.NodeSetterRepository
import com.mishbanya.sudokucompleter.domain.repository.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.repository.SudokuValidityChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SudokuViewModel @Inject constructor(
    private val sudokuGenerator: SudokuGenerator,
    private val nodeSetter: NodeSetterRepository,
    private val backtrackingSolverRepository: BacktrackingSolverRepository
): ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var _field : MutableStateFlow<SudokuField?> = MutableStateFlow(null)
    val field: StateFlow<SudokuField?>
        get() = _field.asStateFlow()

    private var _difficulty: MutableStateFlow<DifficultyLevel> = MutableStateFlow(DifficultyLevel.EASY)
    val difficulty: StateFlow<DifficultyLevel>
        get() = _difficulty.asStateFlow()

    fun setDifficulty(difficulty: DifficultyLevel){
        _difficulty.value = difficulty
    }

    fun generateSudoku(
        onGenerated: () -> Unit
    ) {
        scope.launch {
            _field.value = sudokuGenerator.generateInitialSudoku(difficulty = _difficulty.value)
        }
    }

    fun setNode(
        row: Int,
        col: Int,
        value: Int?
    ): Boolean {
        nodeSetter.setNode(_field.value!!, row, col, value)?.let {
            _field.value = it
            return true
        } ?: return false
    }

    fun solveSudoku(
        onSolved: (Boolean) -> Unit
    ) {
        scope.launch {
            val result = _field.value?.let { initField ->
                backtrackingSolverRepository.solve(
                    field = initField,
                    onUpdate = { updatedField ->
                        _field.value = updatedField
                    }
                )
            }
            onSolved(result ?: false)
        }
    }
}