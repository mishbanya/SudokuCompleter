package com.mishbanya.sudokucompleter.ui.view.sudoku

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mishbanya.sudokucompleter.R
import com.mishbanya.sudokucompleter.data.sudoku.DifficultyLevel
import com.mishbanya.sudokucompleter.ui.viewmodel.SudokuViewModel

@Composable
fun SudokuView(
    sudokuViewModel: SudokuViewModel,
    modifier: Modifier = Modifier,
) {
    val field = sudokuViewModel.field.collectAsState()
    var solvingState by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        sudokuViewModel.getSettings()
    }

    if(field.value == null){
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            SudokuGeneratorComponent(
                modifier = Modifier.align(Alignment.Center),
                sudokuViewModel = sudokuViewModel
            )
        }
    }else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            SudokuGeneratorComponent(
                sudokuViewModel = sudokuViewModel,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                enabled = !solvingState
            )

            Spacer(modifier = Modifier.height(20.dp))

            SudokuMainFieldView(
                sudokuField = field.value!!,
                sudokuViewModel = sudokuViewModel,
                modifier = Modifier
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    solvingState = true
                    sudokuViewModel.solveSudoku(
                        onSolved = {
                            solvingState = false
                        }
                    )
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth(0.5f),
                enabled = !solvingState
            ) {
                Text(
                    text = stringResource(R.string.solve_sudoku),
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun SudokuGeneratorComponent(
    sudokuViewModel: SudokuViewModel,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val difficultyLevel = sudokuViewModel.difficulty.collectAsState()
    var isGenerating by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DifficultyLevel.entries.forEach { level ->
                Button(
                    onClick = { sudokuViewModel.setDifficulty(level) },

                    modifier =
                    if(difficultyLevel.value == level)
                        Modifier
                            .size(50.dp)
                            .border(2.dp, Color.Yellow, RoundedCornerShape(20.dp))
                    else
                        Modifier
                            .size(50.dp)
                    ,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = level.toIndicator(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
        Button(
            onClick = {
                isGenerating = true
                sudokuViewModel.generateSudoku(
                    onGenerated = {
                        isGenerating = false
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(0.5f),
            enabled = enabled && !isGenerating
        ) {
            Text(
                text = stringResource(R.string.generate_sudoku),
                color = Color.White,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}