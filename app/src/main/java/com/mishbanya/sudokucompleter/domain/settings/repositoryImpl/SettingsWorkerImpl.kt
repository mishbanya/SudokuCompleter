package com.mishbanya.sudokucompleter.domain.settings.repositoryImpl

import android.content.SharedPreferences
import com.mishbanya.sudokucompleter.data.common.CONSTANTS.SETTINGS_SP_KEY
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsWorker
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SettingsWorkerImpl(
    private val sharedPreferences: SharedPreferences
): SettingsWorker {

    override fun getFromSharedPreferences(): SettingsModel? {
        return try {
            val jsonSettings = sharedPreferences.getString(SETTINGS_SP_KEY, null)
            if (jsonSettings != null) {
                Json.decodeFromString<SettingsModel>(jsonSettings)
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

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