package com.mishbanya.sudokucompleter.ui.view

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mishbanya.sudokucompleter.ui.viewmodel.SudokuViewModel

@Composable
fun SudokuView(
    modifier: Modifier = Modifier,
    sudokuViewModel: SudokuViewModel = hiltViewModel()
) {
    val field = sudokuViewModel.field.collectAsState()

    Surface(
        modifier = modifier,
        color = Color(0xFFF0F0F0),
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            field.value?.let { field ->
                SudokuFieldView(field, modifier)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    sudokuViewModel.generateSudoku()
                },
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(Color.Blue)
            ) {
                Text(
                    text = "Сгенерировать судоку",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Button(
                onClick = {
                    sudokuViewModel.solveSudoku()
                },
                modifier = modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f),
                colors = ButtonDefaults.buttonColors(Color.Blue),
                enabled = field.value != null
            ) {
                Text(
                    text = "Начать решение",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}