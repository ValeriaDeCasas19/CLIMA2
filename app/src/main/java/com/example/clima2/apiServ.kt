package com.example.clima2


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface apiServ {
    @GET("weather")
    fun obtenerClima(
        @Query("q") ciudad: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Call<ClimaRespuesta>
}