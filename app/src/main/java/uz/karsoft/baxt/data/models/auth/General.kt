package uz.karsoft.baxt.data.models.auth

sealed class General<out T> {
    object Empty : General<Nothing>()
    object Loading : General<Nothing>()
    data class SuccessData<out T>(val data: T) : General<T>()
    data class Error(val message: String?) : General<Nothing>()
    data class NetworkError(val msg: String?) : General<Nothing>()
}