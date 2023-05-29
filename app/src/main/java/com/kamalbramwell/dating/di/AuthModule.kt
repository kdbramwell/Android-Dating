package com.kamalbramwell.dating.di

import com.kamalbramwell.dating.registration.data.AuthDataSource
import com.kamalbramwell.dating.registration.data.LocalAuthDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthDataSource(
        authDataSource: LocalAuthDataSource
    ): AuthDataSource
}