package uz.karsoft.baxt.data.remote

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import uz.karsoft.baxt.data.models.auth.AuthSuccess
import uz.karsoft.baxt.data.models.main.all_markets_data.AllMarketsData
import uz.karsoft.baxt.data.models.main.foods.AllFoodsData
import uz.karsoft.baxt.data.models.main.home.Collections
import uz.karsoft.baxt.data.models.main.home.detail.all_products.AllProductsData
import uz.karsoft.baxt.data.models.main.home.detail.product.ProductData
import uz.karsoft.baxt.data.models.main.home.detail.product.Products
import uz.karsoft.baxt.data.models.main.home.detail.product_by_id_data.ProductByIdData
import uz.karsoft.baxt.data.models.main.market_by_id_data.MarketByIdData
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

    @GET("/api/v1/customer/products/{id}")
    suspend fun getProductsByCategory(@Header("Authorization") token: String, @Path("id") id: Int,@Header("Accept") aplicationJson: String): Response<ProductByIdData>

    @GET("/api/v1/customer/products")
    suspend fun getAllProducts(@Header("Authorization") token: String, @Query("market_id") id: Int): Response<AllProductsData>

    @GET("/api/v1/customer/foods")
    suspend fun getAllFoods(@Header("Authorization") token: String): Response<AllFoodsData>

    @GET("/api/v1/customer/markets")
    suspend fun getAllMarkets(@Header("Authorization") token: String): Response<AllMarketsData>

    @GET("/api/v1/customer/markets/{id}")
    suspend fun marketById(@Header("Authorization") token: String,@Path("id") id : Int): Response<MarketByIdData>



}