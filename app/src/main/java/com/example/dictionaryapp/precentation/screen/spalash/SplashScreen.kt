package com.example.dictionaryapp.precentation.screen.spalash

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dictionaryapp.R
import com.example.dictionaryapp.precentation.screen.menu.MenuScreen

@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment(R.layout.screen_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Ensure the view is associated with a NavController
        val navController = findNavController()

        // Delay navigation
        Handler().postDelayed({
            // Use the NavController obtained above
            navController.navigate(SplashScreenDirections.actionSplashScreenToMenuScreen())
        }, 1000)
    }
}
