package utt.equipo.hackathon.notifications

data class NotificationData(
    val type: String,
    val title: String,
    val body: String,
    val timestamp: String
)

expect class NotificationManager {
    suspend fun initialize()
    suspend fun requestNotificationPermission(): Boolean
    fun hasNotificationPermission(): Boolean
    suspend fun getFCMToken(): String?
    fun setupNotificationHandler(onNotificationReceived: (NotificationData) -> Unit)
}
