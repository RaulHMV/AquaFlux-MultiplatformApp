package com.aquaflux.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import utt.equipo.hackathon.App

/**
 * MainActivity principal de la aplicación Android AquaFlux
 * Configura la Activity y lanza la application Compose
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
