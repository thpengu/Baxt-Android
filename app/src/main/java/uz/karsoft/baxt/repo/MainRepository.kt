package uz.karsoft.baxt.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.search.Categories
import uz.karsoft.baxt.data.remote.ApiInterface
import uz.karsoft.baxt.settings.Settings
import java.io.IOException

class MainRepository (private val apiService: ApiInterface, settings: Settings) {
    private val token = "Bearer ${settings.token}"

    fun getCollections(): Flow<General<Collections>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getCollections(token)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        emit(General.SuccessData(body))
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

    fun getCategories(): Flow<General<Categories>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getCategories(token)
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        emit(General.SuccessData(body))
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