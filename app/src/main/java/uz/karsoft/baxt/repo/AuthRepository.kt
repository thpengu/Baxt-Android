package uz.karsoft.baxt.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.auth.AuthSuccess
import uz.karsoft.baxt.data.remote.ApiInterface
import uz.karsoft.baxt.settings.Settings
import java.io.IOException

class AuthRepository(private val apiService: ApiInterface, private val settings: Settings) {
    fun signIn(phone: String, password: String): Flow<General<AuthSuccess>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.signIn(phone, password)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        emit(General.SuccessData(body))
                        settings.token = body.data.token
                        settings.loggedIn = true
                    } ?: emit(General.Error("No data received"))
                } else {
                    emit(General.Error("Error: ${response.code()} ${response.message()}"))
                }
            } catch (e: IOException) {
                emit(General.NetworkError(e.message))
            } catch (e: HttpException) {
                emit(General.Error("HTTP error: ${e.message}"))
            }
        }

    fun signUp(
        name: String,
        phone: String,
        password: String,
    ): Flow<General<AuthSuccess>> = flow {
        emit(General.Loading)
        try {
            val response = apiService.signUp(name, phone, password)
            if (response.isSuccessful) {
                response.body()?.let { body ->

                    emit(General.SuccessData(body))
                    settings.token = body.data.token
                    settings.loggedIn = true
                } ?: emit(General.Error("No data received"))
            } else {
                emit(General.Error("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: IOException) {
            emit(General.NetworkError(e.message))
        } catch (e: HttpException) {
            emit(General.Error("HTTP error: ${e.message}"))
        }
    }
}
