package ru.glitchless.server.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.glitchless.server.data.models.Message
import ru.glitchless.server.data.models.UserModel

interface IServerApi {
    @POST("/api/signup")
    fun signup(@Body user: UserModel): Call<Message<UserModel>>

    @POST("/api/login")
    fun login(@Body user: UserModel): Call<Message<UserModel>>

    @POST("/api/logout")
    fun logout(@Header("Cookie") session: String? = null): Call<Void>

    @POST("/api/user/change")
    fun update(@Body user: UserModel): Call<Message<UserModel>>

    @GET("/api/user")
    fun me(@Header("Cookie") session: String? = null): Call<Message<UserModel>>
}