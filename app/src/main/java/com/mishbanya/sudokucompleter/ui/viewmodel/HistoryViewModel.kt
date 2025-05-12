package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.domain.history.repository.HistoryWorker
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
    private val historyWorker: HistoryWorker
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _history: MutableStateFlow<List<SudokuField>> = MutableStateFlow(emptyList())
    val history: StateFlow<List<SudokuField>>
        get() = _history.asStateFlow()

    private val _isExpandable: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isExpandable: StateFlow<Boolean>
        get() = _isExpandable.asStateFlow()

    fun clearHistory(){
        historyWorker.clearHistory()
    }

    fun expandHistory(){
        val currentSize = _history.value.size
        val limit = min(_history.value.size+5,historyWorker.getHistorySize()-1)
        scope.launch {
            _history.emit(
                historyWorker.getHistory(currentSize,limit)
            )
            _isExpandable.emit(
                limit != historyWorker.getHistorySize()-1
            )
        }
    }
    
    init {
        expandHistory()
    }
}