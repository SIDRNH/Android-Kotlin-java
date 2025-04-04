package com.example.retrofitpractice.di.manual

import android.app.Application

class ManualApplication: Application() {
    companion object{
        lateinit var appModule: ManualAppModule;
    }

    override fun onCreate() {
        super.onCreate()
        appModule = ManualAppModuleImpl(this)
    }
}