package data.remote

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

/**
 * Engine específico para iOS
 */
actual fun getHttpClientEngine(): HttpClientEngine {
    return Darwin.create()
}
