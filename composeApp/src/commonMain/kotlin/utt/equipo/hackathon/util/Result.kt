package utt.equipo.hackathon.util

/**
 * Sealed class para manejo de estados de operaciones asíncronas
 * Permite manejar éxito, error y estados de carga de forma segura
 */
sealed class Result<out T> {
    /**
     * Operación exitosa con datos
     */
    data class Success<T>(val data: T) : Result<T>()
    
    /**
     * Operación fallida con mensaje de error
     */
    data class Error(
        val message: String,
        val code: Int? = null,
        val exception: Exception? = null
    ) : Result<Nothing>()
    
    /**
     * Estado de carga
     */
    data object Loading : Result<Nothing>()
    
    /**
     * Ejecuta una acción si el resultado es exitoso
     */
    inline fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) {
            action(data)
        }
        return this
    }
    
    /**
     * Ejecuta una acción si el resultado es un error
     */
    inline fun onError(action: (Error) -> Unit): Result<T> {
        if (this is Error) {
            action(this)
        }
        return this
    }
    
    /**
     * Obtiene el dato si es exitoso, o null si es error
     */
    fun getOrNull(): T? {
        return if (this is Success) data else null
    }
    
    /**
     * Obtiene el dato si es exitoso, o un valor por defecto
     */
    fun getOrDefault(defaultValue: @UnsafeVariance T): T {
        return if (this is Success) data else defaultValue
    }
    
    /**
     * Transforma el dato si es exitoso
     */
    inline fun <R> map(transform: (T) -> R): Result<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Error -> this
            is Loading -> this
        }
    }
}

/**
 * Extension function para convertir un bloque de código en Result
 */
inline fun <T> resultOf(block: () -> T): Result<T> {
    return try {
        Result.Success(block())
    } catch (e: Exception) {
        Result.Error(
            message = e.message ?: "Error desconocido",
            exception = e
        )
    }
}
