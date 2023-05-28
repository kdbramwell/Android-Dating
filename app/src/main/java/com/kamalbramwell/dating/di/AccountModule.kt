package com.kamalbramwell.dating.di

import com.kamalbramwell.dating.registration.data.AccountDataSource
import com.kamalbramwell.dating.registration.data.LocalAccountDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class AccountModule {

    @Binds
    abstract fun bindAccountDataSource(
        localAccountDataSource: LocalAccountDataSource
    ): AccountDataSource
}