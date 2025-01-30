package com.mishbanya.sudokucompleter.data.common

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
object RandomModule {

    @Provides
    @Singleton
    fun provideRandom() : Random{
        Log.d("Hilt", "Creating Random.Default client instance")
        return Random.Default
    }
}