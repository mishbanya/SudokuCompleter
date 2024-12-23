package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishbanya.sudokucompleter.data.sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.domain.sudoku.repository.BacktrackingSolver
import com.mishbanya.sudokucompleter.domain.sudoku.repository.NodeSetter
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SolvedObserver
import com.mishbanya.sudokucompleter.domain.sudoku.repository.SudokuGenerator
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
    private val nodeSetter: NodeSetter,
    private val backtrackingSolver: BacktrackingSolver,
    private val solvedObserver: SolvedObserver
): ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private var _field : MutableStateFlow<SudokuField?> = MutableStateFlow(null)
    val field: StateFlow<SudokuField?>
        get() = _field.asStateFlow()

    private var _difficulty: MutableStateFlow<DifficultyLevel> = MutableStateFlow(DifficultyLevel.EASY)
    val difficulty: StateFlow<DifficultyLevel>
        get() = _difficulty.asStateFlow()

    private var _isSolvedField : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isSolvedField: StateFlow<Boolean>
        get() = _isSolvedField.asStateFlow()

    fun setDifficulty(difficulty: DifficultyLevel){
        _difficulty.value = difficulty
    }

    fun generateSudoku() {
        _field.value = sudokuGenerator.generateInitialSudoku(difficulty = _difficulty.value)
        _isSolvedField.value = false
    }

    fun setNode(
        row: Int,
        col: Int,
        value: Int?
    ): Boolean {
        nodeSetter.setNode(_field.value!!, row, col, value)?.let {
            _field.value = it
            solvedObserver.checkSolvedState(_field.value!!)
            return true
        }
        return false
    }

    fun solveSudoku(
        onSolved: (Boolean) -> Unit
    ) {
        scope.launch {
            val result = _field.value?.let { initField ->
                backtrackingSolver.solve(
                    field = initField,
                    onUpdate = { updatedField ->
                        viewModelScope.launch { _field.value = updatedField }
                    },
                    cooldown = 1
                )
            }
            onSolved(result ?: false)
            _isSolvedField.value = result ?: false
        }
    }
}