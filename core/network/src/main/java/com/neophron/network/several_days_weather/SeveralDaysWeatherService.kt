package com.neophron.network.several_days_weather

import retrofit2.http.GET
import retrofit2.http.Path

interface SeveralDaysWeatherService {


    @GET("forecast?q={city_name}&units={units}&lang={lang}")
    suspend fun getWeatherByCityName(
        @Path("city_name") cityName: String,
        @Path("units") units: String?,
        @Path("lang") lang: String,
    ): DaysWeatherDTOResponse

    @GET("forecast?lat={lat}&lon={lon}&units=metric&lang={lang}")
    suspend fun getTodayByGeoCoordinates(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("units") units: String,
        @Path("lang") lang: String,
    ): DaysWeatherDTOResponse

}