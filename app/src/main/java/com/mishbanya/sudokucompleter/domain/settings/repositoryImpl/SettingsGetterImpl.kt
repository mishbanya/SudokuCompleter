package com.mishbanya.sudokucompleter.domain.settings.repositoryImpl

import android.content.SharedPreferences
import com.mishbanya.sudokucompleter.data.common.CONSTANTS.SETTINGS_SP_KEY
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsGetter
import kotlinx.serialization.json.Json

class SettingsGetterImpl(
    private val sharedPreferences: SharedPreferences
): SettingsGetter {

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

}