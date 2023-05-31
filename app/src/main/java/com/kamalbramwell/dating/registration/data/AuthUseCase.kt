package com.kamalbramwell.dating.registration.data

import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.AccountNotFoundException
import com.kamalbramwell.dating.utils.isEmail
import com.kamalbramwell.dating.utils.isPhoneNumber
import javax.inject.Inject

interface AuthUseCase {
    suspend fun submit(account: String, password: String): Result<Boolean>
}

class LoginUseCase @Inject constructor(
    private val dataSource: AuthDataSource
) : AuthUseCase {

    override suspend fun submit(account: String, password: String): Result<Boolean> =
        with (account) {
            when {
                isPhoneNumber() -> dataSource.loginWithPhone(account, password)
                isEmail() -> dataSource.loginWithEmail(account, password)
                else -> Result.failure(AccountNotFoundException)
            }
        }
}

class CreateAccountUseCase @Inject constructor(
    private val dataSource: AuthDataSource
) : AuthUseCase {

    override suspend fun submit(account: String, password: String): Result<Boolean> =
        with (account.trim()) {
            when {
                isPhoneNumber() -> dataSource.registerPhone(account, password)
                isEmail() -> dataSource.registerEmail(account, password)
                else -> Result.failure(Exception("Invalid email or phone number."))
            }
        }
}