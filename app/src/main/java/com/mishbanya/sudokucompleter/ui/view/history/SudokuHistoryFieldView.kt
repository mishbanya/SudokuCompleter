package com.mishbanya.sudokucompleter.ui.view.history

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mishbanya.sudokucompleter.R
import com.mishbanya.sudokucompleter.data.settings.ColorSettingsModel
import com.mishbanya.sudokucompleter.data.sudoku.SudokuField
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNode
import com.mishbanya.sudokucompleter.data.sudoku.SudokuNodeType
import com.mishbanya.sudokucompleter.ui.viewmodel.HistoryViewModel

@Composable
fun SudokuMainFieldView(
    sudokuField: SudokuField,
    historyViewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {
    val settings by historyViewModel.settings.collectAsState()
    val colorSettingsModel = settings.colorSettingsModel
    val isSolved = historyViewModel.checkIsSolved(sudokuField)
    Text("${stringResource(R.string.difficulty)}: ${sudokuField.difficultyLevel.toStringRes()}")
    sudokuField.field.forEachIndexed { index, row ->
        SudokuRowView(
            row = row,
            index = index,
            colorSettingsModel = colorSettingsModel,
            isSolved = isSolved,
            modifier = modifier.padding(vertical = 2.dp)
        )
    }
}

@Composable
fun SudokuRowView(
    row: Array<SudokuNode>,
    index: Int,
    colorSettingsModel: ColorSettingsModel,
    isSolved: Boolean,
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
                colorSettingsModel = colorSettingsModel,
                isSolved = isSolved,
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
    colorSettingsModel: ColorSettingsModel,
    isSolved: Boolean,
    modifier: Modifier = Modifier
) {

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
            onValueChange = {},
            readOnly = true,
            textStyle = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSolved)
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