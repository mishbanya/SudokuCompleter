package com.mishbanya.sudokucompleter.data.settings

import kotlinx.serialization.Serializable

@Serializable
data class SettingsModel(
    val colorSettingsModel: ColorSettingsModel = ColorSettingsModel(
        manualColor = 0xFF009688,
        automaticColor = 0xFF1E88E5,
        completedColor = 0xFFF48FB1
    ),
    val autoCompletionMethod: AutoCompletionMethod = AutoCompletionMethod.CONSTRAINT_PROPAGATION,
    val autoCompletionCooldown: Int = 1
)
