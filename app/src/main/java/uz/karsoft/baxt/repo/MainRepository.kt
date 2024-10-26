package uz.karsoft.baxt.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import uz.karsoft.baxt.data.models.auth.General
import uz.karsoft.baxt.data.models.main.all_markets_data.AllMarketsData
import uz.karsoft.baxt.data.models.main.foods.AllFoodsData
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllProductsData
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.data.models.main.market_by_id_data.MarketByIdData
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

    fun getCollectionsById(id: Int): Flow<General<Categories>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getCategoriesById(token, id)
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

    fun getProductsById(categoryId: Int): Flow<General<ProductByIdData>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getProductsByCategory(token, categoryId,"application/json")
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

    fun getAllProducts(marketId:Int): Flow<General<AllProductsData>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getAllProducts(token,marketId)
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

    fun getAllFoods(): Flow<General<AllFoodsData>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getAllFoods(token)
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

    fun getAllMarkets(): Flow<General<AllMarketsData>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.getAllMarkets(token)
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

    fun marketById(id: Int): Flow<General<MarketByIdData>> =
        flow {
            emit(General.Loading)
            try {
                val response = apiService.marketById(token,id)
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