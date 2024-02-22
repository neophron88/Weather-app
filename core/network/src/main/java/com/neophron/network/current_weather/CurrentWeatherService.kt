package com.neophron.network.current_weather

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrentWeatherService {


    @GET("weather")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String?,
        @Query("lang") lang: String,
    ): CurrentWeatherResponse

    @GET("weather")
    suspend fun getTodayWeatherByGeoCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String?,
        @Query("lang") lang: String,
    ): CurrentWeatherResponse

}