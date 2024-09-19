package uz.karsoft.baxt.data.models.auth

sealed class Auth<out T> {
    object Empty : Auth<Nothing>()
    object Loading : Auth<Nothing>()
    data class SuccessData<out T>(val data: T) : Auth<T>()
    data class Error(val message: String?) : Auth<Nothing>()
    data class NetworkError(val msg: String?) : Auth<Nothing>()
}