package com.mishbanya.sudokucompleter.domain.settings

import android.content.SharedPreferences
import android.util.Log
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsGetter
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsSaver
import com.mishbanya.sudokucompleter.domain.settings.repositoryImpl.SettingsGetterImpl
import com.mishbanya.sudokucompleter.domain.settings.repositoryImpl.SettingsSaverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsSaver(
        sharedPreferences: SharedPreferences
    ): SettingsSaver {
        Log.d("Hilt", "Creating SettingsSaver client instance")
        return SettingsSaverImpl(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideSettingsGetter(
        sharedPreferences: SharedPreferences
    ): SettingsGetter {
        Log.d("Hilt", "Creating SettingsGetter client instance")
        return SettingsGetterImpl(sharedPreferences)
    }
}