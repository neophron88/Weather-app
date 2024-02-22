package com.neophron.network.several_days_weather

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeveralDaysWeatherService {


    @GET("forecast")
    suspend fun getWeatherByCityName(
        @Query("q") cityName: String,
        @Query("units") units: String?,
        @Query("lang") lang: String,
    ): DaysWeatherDTOResponse

    @GET("forecast")
    suspend fun getTodayByGeoCoordinates(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("units") units: String,
        @Query("lang") lang: String,
    ): DaysWeatherDTOResponse

}