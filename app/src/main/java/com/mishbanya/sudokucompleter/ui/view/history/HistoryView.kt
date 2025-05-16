package com.mishbanya.sudokucompleter.ui.view.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mishbanya.sudokucompleter.R
import com.mishbanya.sudokucompleter.ui.viewmodel.HistoryViewModel

@Composable
fun HistoryView(
    historyViewModel: HistoryViewModel,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        historyViewModel.initHistory()
        historyViewModel.getSettings()
    }
    val history by historyViewModel.history.collectAsState()
    val isExpandable by historyViewModel.isExpandable.collectAsState()

    if (history.isNotEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    historyViewModel.clearHistory()
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(stringResource(R.string.history_clear))
            }
            history.forEach {
                SudokuMainFieldView(
                    it,
                    historyViewModel
                )
            }
            if (isExpandable) {
                Button(
                    onClick = {
                        historyViewModel.expandHistory()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(stringResource(R.string.history_load_more))
                }
            }
        }
    }else{
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp, end = 16.dp, start = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.history_no_history)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}