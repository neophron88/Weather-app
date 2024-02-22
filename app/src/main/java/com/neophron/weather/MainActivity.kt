package com.neophron.weather

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)
        val systemBarStyle = SystemBarStyle.dark(Color.TRANSPARENT)
        enableEdgeToEdge(navigationBarStyle = systemBarStyle, statusBarStyle = systemBarStyle)
    }
}