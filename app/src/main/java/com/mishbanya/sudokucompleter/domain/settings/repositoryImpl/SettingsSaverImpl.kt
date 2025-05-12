package com.mishbanya.sudokucompleter.domain.settings.repositoryImpl

import android.content.SharedPreferences
import com.mishbanya.sudokucompleter.data.common.CONSTANTS.SETTINGS_SP_KEY
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsSaver
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsSaverImpl(
    private val sharedPreferences: SharedPreferences,
) : SettingsSaver {

    override fun saveInSharedPreferences(settingsModel: SettingsModel): Boolean {
        return try {
            val jsonSettings = Json.encodeToString(settingsModel)
            sharedPreferences.edit().putString(SETTINGS_SP_KEY, jsonSettings).apply()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
