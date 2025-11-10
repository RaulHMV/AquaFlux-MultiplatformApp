package utt.equipo.hackathon.notifications

import android.Manifest
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

actual class NotificationManager(private val context: Context, private val activity: ComponentActivity) {

    private var permissionContinuation: ( (Boolean) -> Unit )? = null

    private val permissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionContinuation?.let { cont ->
            cont(granted)
            permissionContinuation = null
        }
    }

    actual suspend fun initialize() {
        // Ensure Firebase auto-init (no-op if not needed)
        FirebaseMessaging.getInstance().isAutoInitEnabled = true
        // attempt to warm token
        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { }
        } catch (_: Exception) {}
    }

    actual suspend fun requestNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true

        return suspendCancellableCoroutine { cont ->
            permissionContinuation = { granted ->
                cont.resume(granted)
            }
            try {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } catch (e: Exception) {
                permissionContinuation = null
                cont.resumeWithException(e)
            }
        }
    }

    actual fun hasNotificationPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return true
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }

    actual suspend fun getFCMToken(): String? {
        return try {
            suspendCancellableCoroutine { cont ->
                FirebaseMessaging.getInstance().token
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            cont.resume(task.result)
                        } else {
                            cont.resume(null)
                        }
                    }
            }
        } catch (e: Exception) {
            null
        }
    }

    actual fun setupNotificationHandler(onNotificationReceived: (NotificationData) -> Unit) {
        // Android notifications are handled by FirebaseMessagingService (MyFirebaseMessagingService)
        // that will forward events to app-level handlers via local broadcasts or other mechanisms.
        // Here we leave a no-op placeholder.
    }
}
