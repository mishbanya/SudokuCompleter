package com.mishbanya.sudokucompleter.domain.history.repositoryImpl

import android.content.SharedPreferences
import com.mishbanya.sudokucompleter.data.common.CONSTANTS.HISTORY_SP_KEY
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.domain.history.repository.HistoryWorker
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HistoryWorkerImpl(
    private val sharedPreferences: SharedPreferences
) : HistoryWorker {

    private val sizeKey = "$HISTORY_SP_KEY-SIZE"
    private val historyKey = "$HISTORY_SP_KEY-SUDOKU-AT-"

    override fun saveSudoku(sudokuField: SudokuField): Boolean {
        return try {
            val size = getHistorySize()
            val jsonField = Json.encodeToString(sudokuField)
            sharedPreferences.edit()
                .putString("$historyKey$size", jsonField)
                .putInt(sizeKey, size + 1)
                .apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun getHistorySize(): Int {
        return try {
            sharedPreferences.getInt(sizeKey, 0)
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    override fun getHistory(from: Int, to: Int): List<SudokuField> {
        if (to <= from) return emptyList()
        return try {
            (getHistorySize()-from downTo   getHistorySize()-to).mapNotNull { i ->
                sharedPreferences.getString("$historyKey$i", null)?.let {
                    Json.decodeFromString<SudokuField>(it)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun clearHistory(): Boolean {
        return try {
            val size = getHistorySize()
            sharedPreferences.edit().apply {
                remove(sizeKey)
                for (i in 0 until size) {
                    remove("$historyKey$i")
                }
            }.apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}