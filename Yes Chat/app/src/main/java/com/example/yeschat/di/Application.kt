package com.example.yeschat.di

import android.app.Application

class Application: Application() {
    companion object{
        lateinit var appModule: AppModule;
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl(this)
    }
}