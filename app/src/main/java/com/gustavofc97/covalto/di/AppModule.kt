package com.gustavofc97.covalto.di

import com.gustavofc97.covalto.utlis.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider =
        CoroutineContextProvider(Dispatchers.Main, Dispatchers.IO)
}