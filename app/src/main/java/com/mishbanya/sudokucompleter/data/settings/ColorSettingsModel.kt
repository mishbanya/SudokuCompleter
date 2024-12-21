package com.mishbanya.sudokucompleter.data.settings

import kotlinx.serialization.Serializable

@Serializable
data class ColorSettingsModel(
    val manualColor: Long,
    val automaticColor: Long,
    val completedColor: Long
)
