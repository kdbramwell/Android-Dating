package com.kamalbramwell.dating.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DispatcherModule::class]
)
object FakeDispatcherModule {
    private val testDispatcher = StandardTestDispatcher()

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = testDispatcher

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = testDispatcher
}