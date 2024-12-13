package com.mishbanya.sudokucompleter.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.ui.viewmodel.SudokuViewModel
import com.mishbanya.sudokucompleter.data.SudokuNode

@Composable
fun SudokuView(
    modifier: Modifier = Modifier,
    sudokuViewModel: SudokuViewModel = hiltViewModel()
) {
    val field: SudokuField? by sudokuViewModel.field.collectAsState(initial = null)
    Surface(
        modifier = modifier.padding(5.dp)
    ) {
        field?.field.let { field ->
            field?.forEach { row ->
                SudokuRowView(row, modifier)
            }
        }
        Button(
            modifier = modifier,
            onClick = {
                sudokuViewModel.generateSudoku()
                println(sudokuViewModel.field.value)
            }
        ) {
            Text("Сгенерировать судоку")
        }
    }
}

@Composable
fun SudokuRowView(
    row: Array<SudokuNode>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        row.forEach { cell ->
            SudokuCellView(cell, modifier)
        }
    }
}

@Composable
fun SudokuCellView(
    cell: SudokuNode,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(cell.value.toString())
    }
}
