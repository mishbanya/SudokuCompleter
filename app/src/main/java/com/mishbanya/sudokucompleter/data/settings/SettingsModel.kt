package com.mishbanya.sudokucompleter.data.settings

import com.mishbanya.sudokucompleter.ui.theme.CoralRed
import kotlinx.serialization.Serializable
import com.mishbanya.sudokucompleter.ui.theme.DeepBlue
import com.mishbanya.sudokucompleter.ui.theme.Teal

@Serializable
data class SettingsModel(
    val colorSettingsModel: ColorSettingsModel = ColorSettingsModel(
        manualColor = CoralRed.value,
        automaticColor = DeepBlue.value,
        completedColor = Teal.value
    ),
    val autoCompletionMethod: AutoCompletionMethod = AutoCompletionMethod.CONSTRAINT_PROPAGATION,
    val autoCompletionCooldown: Int = 1,
    val prematureGeneration: Boolean = false,
)
