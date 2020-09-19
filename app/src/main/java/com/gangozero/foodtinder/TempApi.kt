package com.gangozero.foodtinder

class TempApi : Api {
    override suspend fun login(loginData: LoginData): User {
        Thread.sleep(3000)
        return User("X")
    }

    override suspend fun getProducts(): List<Product> {
        Thread.sleep(3000)
        return listOf(Product("A"), Product("B"))
    }

}