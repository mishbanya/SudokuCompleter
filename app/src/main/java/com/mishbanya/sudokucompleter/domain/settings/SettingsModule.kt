package com.mishbanya.sudokucompleter.domain.settings

import android.content.SharedPreferences
import android.util.Log
import com.mishbanya.sudokucompleter.domain.settings.repository.SettingsWorker
import com.mishbanya.sudokucompleter.domain.settings.repositoryImpl.SettingsWorkerImpl
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
    fun provideSettingsGetter(
        sharedPreferences: SharedPreferences
    ): SettingsWorker {
        Log.d("Hilt", "Creating SettingsWorker client instance")
        return SettingsWorkerImpl(sharedPreferences)
    }
}