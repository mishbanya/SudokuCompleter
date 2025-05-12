package com.mishbanya.sudokucompleter.ui.view.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.TextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mishbanya.sudokucompleter.R
import com.mishbanya.sudokucompleter.data.settings.AutoCompletionMethod
import com.mishbanya.sudokucompleter.ui.theme.*
import com.mishbanya.sudokucompleter.ui.viewmodel.SettingsViewModel

@Composable
fun SettingsView(
    settingsViewModel: SettingsViewModel,
    modifier: Modifier = Modifier,
) {
    val settings by settingsViewModel.settingsModel.collectAsState()

    var manualColorField by remember { mutableStateOf(settings.colorSettingsModel.manualColor) }
    var automaticColorField by remember { mutableStateOf(settings.colorSettingsModel.automaticColor) }
    var completedColorField by remember { mutableStateOf(settings.colorSettingsModel.completedColor) }
    var autoCompletionMethodField by remember { mutableStateOf(settings.autoCompletionMethod) }
    var autoCompletionCooldownField by remember { mutableStateOf<Int?>(settings.autoCompletionCooldown) }
    var prematureGenerationField by remember { mutableStateOf(settings.prematureGeneration) }

    val autoCompletionCooldownError = autoCompletionCooldownField == null || autoCompletionCooldownField!! > 1000

    val enableSaving =
        !autoCompletionCooldownError &&
        (manualColorField != settings.colorSettingsModel.manualColor ||
        automaticColorField != settings.colorSettingsModel.automaticColor ||
        completedColorField != settings.colorSettingsModel.completedColor||
        autoCompletionMethodField != settings.autoCompletionMethod||
        autoCompletionCooldownField != settings.autoCompletionCooldown||
        prematureGenerationField != settings.prematureGeneration)

    val manualColorOptions = mutableMapOf(
        CoralRed.value to false,
        SoftPink.value to false,
        Lavender.value to false,
        WarmBrown.value to false
    )
    manualColorOptions.keys.forEach {
        manualColorOptions[it] = it == manualColorField
    }
    val automaticColorOptions = mutableMapOf(
        DeepBlue.value to false,
        SoftGreen.value to false,
        BrightYellow.value to false,
        VibrantOrange.value to false
    )
    automaticColorOptions.keys.forEach {
        automaticColorOptions[it] = it == automaticColorField
    }
    val completedColorOptions = mutableMapOf(
        Teal.value to false,
        RichPurple.value to false,
        LimeGreen.value to false,
        SoftCyan.value to false
    )
    completedColorOptions.keys.forEach {
        completedColorOptions[it] = it == completedColorField
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, end = 16.dp, start = 16.dp)
    ) {
        Text(
            stringResource(R.string.settings_manual_color),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            manualColorOptions.forEach { (color, chosen) ->
                Button(
                    onClick = {
                        manualColorField = color
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(color)),
                    shape = RoundedCornerShape(5.dp),
                    border = if(chosen) BorderStroke(1.dp, SunsetOrange) else BorderStroke(1.dp, Color.Black)
                ) {}
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings_auto_color),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            automaticColorOptions.forEach { (color, chosen) ->
                Button(
                    onClick = {
                        automaticColorField = color
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(color)),
                    shape = RoundedCornerShape(5.dp),
                    border = if(chosen) BorderStroke(1.dp, SunsetOrange) else BorderStroke(1.dp, Color.Black)
                ) {}
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings_completed_color),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            completedColorOptions.forEach { (color, chosen) ->
                Button(
                    onClick = {
                        completedColorField = color
                    },
                    modifier = Modifier.size(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(color)),
                    shape = RoundedCornerShape(5.dp),
                    border = if(chosen) BorderStroke(1.dp, SunsetOrange) else BorderStroke(1.dp, Color.Black)
                ) {}
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings_method),
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AutoCompletionMethod.entries.forEach{method ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(80.dp)
                        .clickable { autoCompletionMethodField = method }
                        .padding(8.dp)
                        .border(
                            width = 2.dp,
                            color = if(autoCompletionMethodField == method)
                                SunsetOrange
                            else
                                Color.Black,
                            shape = RoundedCornerShape(5.dp)
                        )
                ) {
                    Text(
                        text = method.toStringRes(),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(5.dp).align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings_cooldown),
            textAlign = TextAlign.Center
        )
        TextField(
            value = autoCompletionCooldownField?.toString() ?: "",
            onValueChange = { newValue ->
                autoCompletionCooldownField = newValue.filter { it.isDigit() }.toIntOrNull()
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            placeholder = { Text(stringResource(R.string.settings_cooldown)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = autoCompletionCooldownError,
            colors = TextFieldDefaults.colors().copy(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                errorContainerColor = Color.Red.copy(alpha = 0.3f)
            ),
            trailingIcon = {
                Text(
                    stringResource(R.string.mseconds)
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            stringResource(R.string.settings_pregeneration),
            textAlign = TextAlign.Center
        )
        Switch(
            checked = prematureGenerationField,
            onCheckedChange = {
                prematureGenerationField = !prematureGenerationField
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
                settingsViewModel.updateSettings(
                    manualColorField,
                    automaticColorField,
                    completedColorField,
                    autoCompletionMethodField,
                    autoCompletionCooldownField,
                    prematureGenerationField
                )
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(5.dp),
            enabled = enableSaving
        ) {
            Text(stringResource(R.string.settings_save))
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}