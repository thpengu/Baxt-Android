package uz.karsoft.baxt.data.remote

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import uz.karsoft.baxt.data.models.auth.AuthSuccess
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.data.models.main.search.Categories

interface ApiInterface {

    @FormUrlEncoded
    @POST("/api/v1/customer/signIn")
    suspend fun signIn(
        @Field("phone") phone: String, @Field("password") password: String
    ): Response<AuthSuccess>


    @FormUrlEncoded
    @POST("/api/v1/customer/signUp")
    suspend fun signUp(
        @Field("name") name: String,
        @Field("phone") phone: String,
        @Field("password") password: String,
    ): Response<AuthSuccess>

    @GET("/api/v1/customer/collections")
    suspend fun getCollections(@Header("Authorization") token: String): Response<Collections>

    @GET("/api/v1/customer/categories")
    suspend fun getCategories(@Header("Authorization") token: String): Response<Categories>

    @GET("/api/v1/customer/categories")
    suspend fun getCategoriesById(@Header("Authorization") token: String, @Query("collection_id") id: Int): Response<Categories>

    @GET("/api/v1/customer/merchandises")
    suspend fun getMerchandisesById(@Header("Authorization") token: String, @Query("category_id") categoryId: Int, @Query("collection_id") collectionId: Int): Response<Products>

    @GET("/api/v1/customer/products")
    suspend fun getProductsByCategory(@Header("Authorization") token: String, @Query("category_id") id: Int): Response<Products>

}