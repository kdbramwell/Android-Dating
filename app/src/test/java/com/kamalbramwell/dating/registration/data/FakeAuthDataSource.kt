package com.kamalbramwell.dating.registration.data

import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.AccountExistsException
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.AccountNotFoundException
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.IncorrectPasswordException
import com.kamalbramwell.dating.registration.model.AccountData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FakeAuthDataSource(
    loggedIn: Boolean = false,
    initialAccounts: Map<String, String> = mapOf(),
) : AuthDataSource {

    private val accounts = initialAccounts.toMutableMap()
    override val isLoggedIn: Flow<Boolean> = MutableStateFlow(loggedIn)
    override val currentUser: Flow<AccountData?> = MutableStateFlow(AccountData("", ""))

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

class FakeAuthDataSourceTest {

    @Test
    fun `logged in returns true`() = runTest {
        FakeAuthDataSource(true).run {
            assertEquals(true, isLoggedIn.first())
        }
    }

    @Test
    fun `not logged in returns false`() = runTest {
        FakeAuthDataSource(false).run {
            assertEquals(false, isLoggedIn.first())
        }
    }

    @Test
    fun `registering an account that exists returns failure`() = runTest {
        val account = "email"
        val password = "password"
        val accounts = mapOf(account to password)
        FakeAuthDataSource(false, accounts).run {
            val result = registerEmail(account, password)
            assertEquals(true, result.isFailure)
        }
    }

    @Test
    fun `logging with incorrect credentials returns failure`() = runTest {
        val accounts = mapOf("email" to "password")
        FakeAuthDataSource(false, accounts).run {
            val result = loginWithEmail("nope", "wrong")
            assertEquals(true, result.isFailure)
        }
    }
}