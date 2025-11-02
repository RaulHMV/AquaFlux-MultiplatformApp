package utt.equipo.hackathon.util

/**
 * Constantes de la aplicación AquaFlux
 */
object Constants {
    /**
     * URLs del API
     * IMPORTANTE: 
     * - Para emulador Android: use http://10.0.2.2:3001/api
     * - Para dispositivo real Android: use http://TU_IP_LOCAL:3001/api
     * - Para iOS Simulator: use http://localhost:3001/api
     * - Para producción: use https://tu-dominio.com/api
     */
    object Api {
        // CAMBIA ESTA URL SEGÚN TU ENTORNO:
        // Para Android Emulator: "http://10.0.2.2:3001/api"
        // Para dispositivo Android real en la misma red: "http://TU_IP:3001/api" (ejemplo: http://192.168.1.100:3001/api)
        // Para iOS Simulator: "http://localhost:3001/api"
        const val BASE_URL = "https://aquaflux.vercel.app" 
        
        // Endpoints de autenticación
        const val LOGIN = "/api/auth/login"
        const val REGISTER = "/api/auth/register"
        
        // Endpoints de feeds
        const val DASHBOARD = "/api/feeds/dashboard"
        const val LEAK_STATUS = "/api/feeds/leak-status"
        const val WATER_FLOW = "/api/feeds/water-flow"
        const val WATER_FLOW_CHART = "/api/feeds/water-flow-chart"
        
        // Timeouts
        const val REQUEST_TIMEOUT = 30000L
        const val CONNECT_TIMEOUT = 30000L
    }
    
    /**
     * Configuración de monitoreo de fugas
     */
    object Monitoring {
        // Intervalo de verificación en milisegundos (30 segundos)
        const val LEAK_CHECK_INTERVAL = 30000L
        
        // Intervalo de actualización del dashboard en milisegundos (30 segundos)
        const val DASHBOARD_REFRESH_INTERVAL = 30000L
    }
    
    /**
     * Claves para SharedPreferences / DataStore
     */
    object Storage {
        const val PREFERENCES_NAME = "aquaflux_preferences"
        const val KEY_AUTH_TOKEN = "auth_token"
        const val KEY_USER_ID = "user_id"
        const val KEY_USER_NAME = "user_name"
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_LEAK_STATUS = "last_leak_status"
        const val KEY_IS_LOGGED_IN = "is_logged_in"
    }
    
    /**
     * Configuración de Firebase Cloud Messaging
     */
    object Firebase {
        const val NOTIFICATION_CHANNEL_ID = "aquaflux_leak_alerts"
        const val NOTIFICATION_CHANNEL_NAME = "Alertas de Fugas"
        const val NOTIFICATION_CHANNEL_DESCRIPTION = "Notificaciones de detección de fugas de agua"
    }
    
    /**
     * Configuración de colores (Material 3)
     */
    object Colors {
        const val NEGRO = 0xFF000814
        const val AZUL_OSCURO = 0xFF001D3D
        const val AZUL_MAS_CLARO = 0xFF003566
        const val AMARILLO_OSCURO = 0xFFFFC300
        const val AMARILLO_CLARO = 0xFFFFD60A
    }
    
    /**
     * Mensajes de error comunes
     */
    object ErrorMessages {
        const val NETWORK_ERROR = "Error de conexión. Verifica tu internet."
        const val UNAUTHORIZED = "Sesión expirada. Por favor inicia sesión nuevamente."
        const val SERVER_ERROR = "Error del servidor. Intenta más tarde."
        const val UNKNOWN_ERROR = "Error desconocido. Intenta nuevamente."
        const val INVALID_CREDENTIALS = "Usuario o contraseña incorrectos."
        const val TIMEOUT_ERROR = "La solicitud tardó demasiado. Intenta nuevamente."
    }
    
    /**
     * Códigos de estado HTTP
     */
    object HttpStatus {
        const val OK = 200
        const val CREATED = 201
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val FORBIDDEN = 403
        const val NOT_FOUND = 404
        const val SERVER_ERROR = 500
    }
}
