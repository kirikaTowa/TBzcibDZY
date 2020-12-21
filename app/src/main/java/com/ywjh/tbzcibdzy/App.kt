package com.ywjh.tbzcibdzy

import android.app.Application
import android.os.Handler

class App: Application() {

    companion object{
        val handler= Handler()//主线程静态handler
    }

    override fun onCreate() {
        super.onCreate()
    }
}
