package utt.equipo.hackathon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import utt.equipo.hackathon.data.local.LocalStorageImpl
import utt.equipo.hackathon.di.DependencyContainer
import utt.equipo.hackathon.notifications.NotificationManager
import utt.equipo.hackathon.notifications.NotificationApiService

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        
        // Inicializar DependencyContainer con LocalStorage
        DependencyContainer.init(LocalStorageImpl(applicationContext))

        // Crear NotificationManager Android y pasar a la App composable
        val androidNotificationManager = NotificationManager(applicationContext, this)
        val notificationApi = DependencyContainer.notificationApiService

        setContent {
            App(notificationManager = androidNotificationManager, notificationApiService = notificationApi)
        }
    }
}