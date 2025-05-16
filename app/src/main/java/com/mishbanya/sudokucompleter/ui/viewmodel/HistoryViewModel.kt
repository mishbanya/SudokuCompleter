package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.domain.history.repository.HistoryWorker
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsWorker
import com.mishbanya.sudokucompleter.domain.sudoku.SolvedObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.min

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyWorker: HistoryWorker,
    private val solvedObserver: SolvedObserver,
    private val settingsWorker: SettingsWorker
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _history: MutableStateFlow<List<SudokuField>> = MutableStateFlow(emptyList())
    val history: StateFlow<List<SudokuField>>
        get() = _history.asStateFlow()

    private val _isExpandable: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isExpandable: StateFlow<Boolean>
        get() = _isExpandable.asStateFlow()

    private val _settings: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())
    val settings: StateFlow<SettingsModel>
        get() = _settings.asStateFlow()

    fun clearHistory(){
        historyWorker.clearHistory()
        initHistory()
    }

    fun getSettings(){
        scope.launch {
            _settings.emit(
                settingsWorker.getFromSharedPreferences() ?: SettingsModel()
            )
        }
    }

    fun initHistory(){
        scope.launch {
            _history.emit(
                emptyList()
            )
            expandHistory()
        }
    }

    fun checkIsSolved(sudokuField: SudokuField): Boolean{
        return solvedObserver.checkSolvedState(sudokuField)
    }

    fun expandHistory(){
        val currentSize = _history.value.size
        val limit = min(_history.value.size+3,historyWorker.getHistorySize())
        scope.launch {
            _history.emit(
                _history.value + historyWorker.getHistory(currentSize,limit)
            )
            _isExpandable.emit(
                limit != historyWorker.getHistorySize()
            )
        }
    }
}