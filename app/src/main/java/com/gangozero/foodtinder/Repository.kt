package com.gangozero.foodtinder

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class Repository {

    private val api = TempApi()

    fun login(loginData: LoginData): Flow<User> {
        return flow {
            emit(api.login(loginData))
        }.flowOn(Dispatchers.IO)
    }
}