package com.mishbanya.sudokucompleter.data.common

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.mishbanya.sudokucompleter.data.common.CONSTANTS.SETTINGS_SP_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreference(
        @ApplicationContext context: Context
    ): SharedPreferences {
        Log.d("Hilt", "Creating SharedPreference client instance")
        return context.getSharedPreferences(SETTINGS_SP_KEY, Context.MODE_PRIVATE)
    }
}
