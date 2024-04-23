package com.example.dictionaryapp.app

import android.app.Application
import com.example.dictionaryapp.data.sourse.DictionaryDatabase
import com.example.dictionaryapp.domain.AppRepositoryImp

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        DictionaryDatabase.init(this)
        AppRepositoryImp.init()
    }
}