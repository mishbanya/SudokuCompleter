package com.mishbanya.sudokucompleter.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mishbanya.sudokucompleter.data.SudokuField
import com.mishbanya.sudokucompleter.data.SudokuNode
import com.mishbanya.sudokucompleter.data.SudokuNodeType

@Composable
fun SudokuFieldView(
    sudokuField: SudokuField,
    modifier: Modifier = Modifier
) {
    Text("Текущий уровень сложности: ${sudokuField.difficultyLevel}")
    sudokuField.field.forEachIndexed { index, row ->
        SudokuRowView(row, index, modifier.padding(vertical = 2.dp))
    }
}

@Composable
fun SudokuRowView(
    row: Array<SudokuNode>,
    index: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(35.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        row.forEachIndexed { index, cell ->
            SudokuCellView(cell, index, Modifier.padding(horizontal = 2.dp))
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

        Text(
            text = cell.value?.toString() ?: "",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = if (cell.flag == SudokuNodeType.Initial) Color.Black else Color.Red
            ),
            textAlign = TextAlign.Center
        )
    }
    if(index==2 || index==5){
        VerticalDivider(color = Color.Black, thickness = 1.dp)
    }
}