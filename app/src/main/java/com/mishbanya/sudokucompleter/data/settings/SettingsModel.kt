package com.mishbanya.sudokucompleter.data.settings

import kotlinx.serialization.Serializable

@Serializable
data class SettingsModel(
    val colorSettingsModel: ColorSettingsModel,
    val autoCompletionMethod: AutoCompletionMethod
)
