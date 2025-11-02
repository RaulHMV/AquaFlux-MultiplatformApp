package utt.equipo.hackathon.data.remote

import io.ktor.client.engine.*
import io.ktor.client.engine.android.*

/**
 * Engine específico para Android
 */
actual fun getHttpClientEngine(): HttpClientEngine {
    return Android.create()
}
