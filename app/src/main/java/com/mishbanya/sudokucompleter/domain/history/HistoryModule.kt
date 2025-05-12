package com.mishbanya.sudokucompleter.domain.history

import android.content.SharedPreferences
import android.util.Log
import com.mishbanya.sudokucompleter.domain.history.repository.HistoryWorker
import com.mishbanya.sudokucompleter.domain.history.repositoryImpl.HistoryWorkerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HistoryModule {

    @Provides
    @Singleton
    fun provideHistoryWorker(
        sharedPreferences: SharedPreferences
    ): HistoryWorker {
        Log.d("Hilt", "Creating HistoryWorker client instance")
        return HistoryWorkerImpl(sharedPreferences)
    }
}