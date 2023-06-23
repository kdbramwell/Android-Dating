package com.kamalbramwell.dating.registration.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.kamalbramwell.dating.di.IoDispatcher
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.AccountNotFoundException
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.IncorrectPasswordException
import com.kamalbramwell.dating.registration.data.AuthDataSource.Exceptions.LoginFailedException
import com.kamalbramwell.dating.registration.model.AccountData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

interface AuthDataSource {
    val isLoggedIn: Flow<Boolean>
    val currentUser: Flow<AccountData?>
    suspend fun registerEmail(email: String, password: String): Result<Boolean>
    suspend fun registerPhone(phone: String, password: String): Result<Boolean>
    suspend fun loginWithEmail(email: String, password: String): Result<Boolean>
    suspend fun loginWithPhone(phone: String, password: String): Result<Boolean>

    companion object Exceptions {
        val AccountExistsException = Exception("Account already exists.")
        val AccountNotFoundException = Exception("Account not found.")
        val IncorrectPasswordException = Exception("Incorrect password.")
        val LoginFailedException = Exception("Login failed.")
    }
}

/**
 * Uses [DataStore] to persist account data.
 */
@Singleton
class LocalAuthDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): AuthDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(dataStoreName)

    override val currentUser: Flow<AccountData?> = context.dataStore.data.map {
        it[userKey]?.let { user -> AccountData("0", user) }
    }
    override val isLoggedIn: Flow<Boolean> = currentUser.map { it != null }

    private suspend fun saveLogin(emailOrPhone: String, password: String): Result<Boolean> =
        withContext(dispatcher) {
            try {
                writeToDataStore(userKey, emailOrPhone)
                writeToDataStore(passwordKey, password)
                Result.success(true)
            } catch (e: Exception) {
                Log.d("[DataStore]", "Error occurred: ${e.message}")
                Result.failure(e)
            }
        }

    private suspend fun writeToDataStore(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { it[key] = value }
    }

    override suspend fun registerEmail(email: String, password: String): Result<Boolean> =
        saveLogin(email, password)

    override suspend fun registerPhone(phone: String, password: String): Result<Boolean> =
        saveLogin(phone, password)

    private val loginFailure = Result.failure<Boolean>(LoginFailedException)

    private suspend fun login(emailOrPhone: String, password: String): Result<Boolean> =
        withContext(dispatcher) {
            try {
                var result: Result<Boolean> = loginFailure
                context.dataStore.updateData {
                    result = when {
                        it[userKey] != emailOrPhone -> Result.failure(AccountNotFoundException)
                        it[passwordKey] != password -> Result.failure(IncorrectPasswordException)
                        else -> Result.success(true)
                    }
                    it
                }

                result
            } catch (e: Exception) {
                loginFailure
            }
        }

    override suspend fun loginWithEmail(email: String, password: String): Result<Boolean> =
        login(email, password)

    override suspend fun loginWithPhone(phone: String, password: String): Result<Boolean> =
        login(phone, password)

    companion object {
        private const val dataStoreName = "account"
        private val userKey = stringPreferencesKey("user")
        private val passwordKey = stringPreferencesKey("password")
    }
}