package com.kamalbramwell.dating.di

import com.kamalbramwell.dating.user.data.DummyUserProfileDataSource
import com.kamalbramwell.dating.user.data.UserProfileDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideUserProfileDataSource(): UserProfileDataSource =
        DummyUserProfileDataSource(both = true)
}