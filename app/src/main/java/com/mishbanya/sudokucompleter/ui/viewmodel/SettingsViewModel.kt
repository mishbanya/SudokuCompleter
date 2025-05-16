package com.mishbanya.sudokucompleter.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.mishbanya.sudokucompleter.data.settings.AutoCompletionMethod
import com.mishbanya.sudokucompleter.data.settings.ColorSettingsModel
import com.mishbanya.sudokucompleter.data.settings.SettingsModel
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsWorker: SettingsWorker
): ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private var _settingsModel: MutableStateFlow<SettingsModel> = MutableStateFlow(SettingsModel())
    val settingsModel: StateFlow<SettingsModel>
        get() = _settingsModel.asStateFlow()

    init {
        getSettings()
    }

    fun updateSettings(
        manualColor: ULong? = null,
        automaticColor: ULong? = null,
        completedColor: ULong? = null,
        autoCompletionMethod: AutoCompletionMethod? = null,
        autoCompletionCooldown: Int? = null,
        prematureGeneration: Boolean? = null
    ){
        scope.launch {
            _settingsModel.emit(
                settingsModel.value.copy(
                    colorSettingsModel = ColorSettingsModel(
                        manualColor = manualColor?: settingsModel.value.colorSettingsModel.manualColor,
                        automaticColor = automaticColor?: settingsModel.value.colorSettingsModel.automaticColor,
                        completedColor = completedColor?: settingsModel.value.colorSettingsModel.completedColor
                    ),
                    autoCompletionMethod = autoCompletionMethod?: settingsModel.value.autoCompletionMethod,
                    autoCompletionCooldown = autoCompletionCooldown?: settingsModel.value.autoCompletionCooldown,
                    prematureGeneration = prematureGeneration?: settingsModel.value.prematureGeneration
                )
            )
            settingsWorker.saveInSharedPreferences(_settingsModel.value)
        }
    }

    private fun getSettings(): Boolean{
        settingsWorker.getFromSharedPreferences()?.let {
            _settingsModel.value = it
            return true
        } ?: return false
    }
}