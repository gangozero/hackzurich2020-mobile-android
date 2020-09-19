package com.gangozero.foodtinder

interface Api {
    suspend fun login(loginData: LoginData): User
    suspend fun getProducts(): List<Product>
}

