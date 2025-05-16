package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mishbanya.sudokucompleter.data.sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.settings.AutoCompletionMethod
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.domain.history.repository.HistoryWorker
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsWorker
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.BacktrackingSolver
import com.mishbanya.sudokucompleter.domain.sudoku.NodeSetter
import com.mishbanya.sudokucompleter.domain.sudoku.SolvedObserver
import com.mishbanya.sudokucompleter.domain.sudoku.generator.SudokuGenerator
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.ConstraintPropagationSolver
import com.mishbanya.sudokucompleter.domain.sudoku.solvers.XAlgorithmSolver
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
    private val xAlgorithmSolver: XAlgorithmSolver,
    private val constraintPropagationSolver: ConstraintPropagationSolver,
    private val solvedObserver: SolvedObserver,
    private val settingsWorker: SettingsWorker,
    private val historyWorker: HistoryWorker
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

    private val _settings: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())
    val settings: StateFlow<SettingsModel>
        get() = _settings.asStateFlow()

    fun getSettings(){
        scope.launch {
            _settings.emit(
                settingsWorker.getFromSharedPreferences() ?: SettingsModel()
            )
        }
    }

    fun setDifficulty(difficulty: DifficultyLevel){
        _difficulty.value = difficulty
    }

    fun generateSudoku(
        onGenerated: () -> Unit
    ) {
        scope.launch {
            _field.value?.let { historyWorker.saveSudoku(it) }
            _field.value = sudokuGenerator.generateInitialSudoku(difficulty = _difficulty.value)
            _isSolvedField.value = false
            onGenerated()
        }
    }

    fun setNode(
        row: Int,
        col: Int,
        value: Int?
    ): Boolean {
        nodeSetter.setNode(_field.value!!, row, col, value)?.let {
            _field.value = it
            scope.launch {
                _isSolvedField.emit(solvedObserver.checkSolvedState(_field.value!!))
            }
            return true
        }
        return false
    }

    fun solveSudoku(
        onSolved: (Boolean) -> Unit
    ) {
        scope.launch {
            val result = _field.value?.let { initField ->
                val solver = when(settings.value.autoCompletionMethod){
                    AutoCompletionMethod.BACKTRACKING -> backtrackingSolver
                    AutoCompletionMethod.CONSTRAINT_PROPAGATION -> constraintPropagationSolver
                    AutoCompletionMethod.DANCING_LINKS_X -> xAlgorithmSolver
                }
                solver.solve(
                    field = initField,
                    onUpdate = { updatedField ->
                        viewModelScope.launch { _field.value = updatedField }
                    },
                    cooldown = settings.value.autoCompletionCooldown
                )
            }
            onSolved(result ?: false)
            _isSolvedField.value = result ?: false
        }
    }
}