package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mishbanya.sudokucompleter.data.settings.AutoCompletionMethod
import com.mishbanya.sudokucompleter.data.settings.ColorSettingsModel
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsGetter
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsSaver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsSaver: SettingsSaver,
    private val settingsGetter: SettingsGetter
): ViewModel() {

    private var _settingsModel: MutableStateFlow<SettingsModel?> = MutableStateFlow(null)
    val settingsModel: StateFlow<SettingsModel?>
        get() = _settingsModel.asStateFlow()

    init {
        if(!getSettings()){
            setInitialSettings()
        }
    }

    private fun setInitialSettings(){
        _settingsModel.value = SettingsModel()
    }

    fun saveSettings(){
        _settingsModel.value?.let { settingsSaver.saveInSharedPreferences(it) }
    }

    fun getSettings(): Boolean{
        settingsGetter.getFromSharedPreferences()?.let {
            _settingsModel.value = it
            return true
        } ?: return false
    }
}