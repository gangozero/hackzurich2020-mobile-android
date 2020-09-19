package com.gangozero.foodtinder

import android.app.Application

class App : Application() {

    companion object {
        lateinit var repository: Repository
    }

    override fun onCreate() {
        super.onCreate()
        repository = Repository(this)
    }
}