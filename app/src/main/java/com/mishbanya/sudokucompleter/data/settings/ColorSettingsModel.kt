package com.mishbanya.sudokucompleter.data.settings

import kotlinx.serialization.Serializable

@Serializable
data class ColorSettingsModel(
    val manualColor: ULong,
    val automaticColor: ULong,
    val completedColor: ULong
)
