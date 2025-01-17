package com.mishbanya.sudokucompleter.domain.settings.repository

import com.mishbanya.sudokucompleter.data.settings.SettingsModel

interface SettingsGetter {
    fun getFromSharedPreferences(): SettingsModel?
}