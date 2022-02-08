package com.audax.projetoseries.interfaces

import com.audax.projetoseries.model.*
import retrofit2.Response
import retrofit2.http.*

interface SeriesAPI {

    @POST("login")
    suspend fun postLogin(@Header("Authorization") authorization: String, @Body user: User): Response<TryLoginResponse>

    @GET("series?per_page=20")
    suspend fun getSeries(@Header("Authorization") accessToken: String): Response<SeriesResponse>

    @POST("series")
    suspend fun postSerie(@Header("Authorization") accessToken: String, @Body serie: Serie): Response<PostSerieResponse>

    @DELETE("series/{id}")
    suspend fun deleteSerie(@Header("Authorization") accessToken: String, @Path ("id") id: String): Response<Void>
}