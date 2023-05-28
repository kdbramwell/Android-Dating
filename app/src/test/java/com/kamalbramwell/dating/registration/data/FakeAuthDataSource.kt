package com.kamalbramwell.dating.registration.data

import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.AccountExistsException
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.AccountNotFoundException
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.IncorrectPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAuthDataSource(
    initialAccounts: Map<String, String>,
    initiallyLoggedIn: Boolean = false,
) : AuthDataSource {

    private val _isLoggedIn = MutableStateFlow(initiallyLoggedIn)
    override val isLoggedIn: Flow<Boolean> = _isLoggedIn

    private val accounts = initialAccounts.toMutableMap()

    override suspend fun registerEmail(email: String, password: String): Result<Boolean> =
        if (accounts.contains(email)) Result.failure(AccountExistsException)
        else save(email, password)

    private fun save(account: String, password: String): Result<Boolean> {
        accounts[account] = password
        return Result.success(true)
    }

    override suspend fun registerPhone(phone: String, password: String): Result<Boolean> =
        registerEmail(phone, password)

    override suspend fun loginWithEmail(email: String, password: String): Result<Boolean> =
        accounts[email]?.let {
            if (it == password) Result.success(true)
            else Result.failure(IncorrectPasswordException)
        } ?: Result.failure(AccountNotFoundException)

    override suspend fun loginWithPhone(phone: String, password: String): Result<Boolean> =
        loginWithEmail(phone, password)
}