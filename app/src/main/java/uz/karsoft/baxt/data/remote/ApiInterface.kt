package uz.karsoft.baxt.data.remote

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import uz.karsoft.baxt.data.models.auth.AuthSuccess

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
}