package com.gangozero.foodtinder

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository(val context: Context) {

    private val api = TempApi()
    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    lateinit var updater: IsLoggedInUpdater

    fun login(loginData: LoginData): Flow<User> {
        return flow {
            val user = api.login(loginData)
            storeUser(user)
            emit(user)
        }.flowOn(Dispatchers.IO)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("user-logged-in", false)
    }

    fun storeUser(user: User) {
        updater.update(true)
        prefs.edit().putBoolean("user-logged-in", true).apply()
    }

    fun logOut() {
        updater.update(false)
        prefs.edit().putBoolean("user-logged-in", false).apply()
    }
}