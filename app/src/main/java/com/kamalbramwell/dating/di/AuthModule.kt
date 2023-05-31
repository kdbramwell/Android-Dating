package com.kamalbramwell.dating.di

import com.kamalbramwell.dating.registration.data.AuthDataSource
import com.kamalbramwell.dating.registration.data.LocalAuthDataSource
import com.kamalbramwell.dating.registration.data.AuthUseCase
import com.kamalbramwell.dating.registration.data.CreateAccountUseCase
import com.kamalbramwell.dating.registration.data.LoginUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Binds
    fun bindAuthDataSource(
        authDataSource: LocalAuthDataSource
    ): AuthDataSource

    @Login
    @Binds
    fun bindLoginUseCase(
        authUseCase: LoginUseCase
    ): AuthUseCase

    @Create
    @Binds
    fun bindCreateAccountUseCase(
        authUseCase: CreateAccountUseCase
    ): AuthUseCase
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Login

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class Create