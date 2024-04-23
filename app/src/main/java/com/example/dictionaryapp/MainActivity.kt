package com.example.dictionaryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dictionaryapp.precentation.screen.menu.MenuScreen
import com.example.dictionaryapp.precentation.screen.seacher.SearchScreen
import com.example.dictionaryapp.precentation.screen.spalash.SplashScreen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportFragmentManager.beginTransaction().add(R.id.container, SplashScreen()).commit()
    }
}

