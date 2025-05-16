package com.mishbanya.sudokucompleter.domain.settings.repository

import com.mishbanya.sudokucompleter.data.settings.SettingsModel

interface SettingsWorker {
    fun getFromSharedPreferences(): SettingsModel?
    fun saveInSharedPreferences(settingsModel: SettingsModel): Boolean
}