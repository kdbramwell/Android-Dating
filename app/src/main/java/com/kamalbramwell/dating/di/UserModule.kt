package com.kamalbramwell.dating.di

import com.kamalbramwell.dating.user.data.DummyUserProfileDataSource
import com.kamalbramwell.dating.user.data.UserProfileDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UserModule {

    @Binds
    fun bindUserProfileDataSource(
        dataSource: DummyUserProfileDataSource
    ): UserProfileDataSource
}