package utt.equipo.hackathon.notifications

actual class NotificationManager {
    actual suspend fun initialize() {
        // No-op for iOS
    }

    actual suspend fun requestNotificationPermission(): Boolean {
        return false
    }

    actual fun hasNotificationPermission(): Boolean {
        return false
    }

    actual suspend fun getFCMToken(): String? {
        return null
    }

    actual fun setupNotificationHandler(onNotificationReceived: (NotificationData) -> Unit) {
        // No-op for iOS
    }
}
