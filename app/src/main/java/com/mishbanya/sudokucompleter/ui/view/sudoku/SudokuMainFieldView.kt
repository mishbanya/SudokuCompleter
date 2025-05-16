package com.mishbanya.sudokucompleter.ui.view.sudoku

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mishbanya.sudokucompleter.R
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.ui.viewmodel.SudokuViewModel

@Composable
fun SudokuMainFieldView(
    sudokuField: SudokuField,
    sudokuViewModel: SudokuViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Text("${stringResource(R.string.difficulty)}: ${sudokuField.difficultyLevel.toStringRes()}")
    sudokuField.field.forEachIndexed { index, row ->
        val tryAgainText = stringResource(R.string.try_again)
        SudokuRowView(
            row = row,
            index = index,
            sudokuViewModel = sudokuViewModel,
            onChanged = { col, value ->
                if(!sudokuViewModel.setNode(index,col,value)){
                    Toast.makeText(context, tryAgainText, Toast.LENGTH_SHORT).show()
                }
            },
            modifier = modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
fun SudokuRowView(
    row: Array<SudokuNode>,
    index: Int,
    sudokuViewModel: SudokuViewModel,
    onChanged: (col:Int, value: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(35.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        row.forEachIndexed { index, cell ->
            SudokuCellView(
                cell = cell,
                index = index,
                sudokuViewModel = sudokuViewModel,
                onChanged = {
                    onChanged(index, it)
                },
                modifier = modifier.padding(horizontal = 2.dp))
        }
    }
    if(index==2 || index==5){
        HorizontalDivider(color = Color.Black, thickness = 1.dp)
    }
}

@Composable
fun SudokuCellView(
    cell: SudokuNode,
    index: Int,
    sudokuViewModel: SudokuViewModel,
    onChanged: (value: Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val settings by sudokuViewModel.settings.collectAsState()
    val isSolved = sudokuViewModel.isSolvedField.collectAsState()
    val colorSettingsModel = settings.colorSettingsModel

    Box(
        modifier = modifier
            .size(35.dp)
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .border(
                1.dp, Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = cell.value?.toString() ?: "",
            onValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    onChanged(null)
                } else if (newValue.length == 1 && newValue.all { it.isDigit() } && newValue.toInt() in 1..9) {
                    onChanged(newValue.toInt())
                }
            },
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSolved.value)
                    Color(colorSettingsModel.completedColor)
                else {
                    when(cell.flag){
                        SudokuNodeType.Filled -> Color(colorSettingsModel.automaticColor)
                        SudokuNodeType.FilledManually -> Color(colorSettingsModel.manualColor)
                        SudokuNodeType.Initial -> Color.Black
                        SudokuNodeType.Unfilled -> Color.Transparent
                    }
                },
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            decorationBox = { innerTextField ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    innerTextField()
                }
            },
        )
    }
    if(index==2 || index==5){
        VerticalDivider(color = Color.Black, thickness = 1.dp)
    }
}