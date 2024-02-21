package com.neophron.network.current_weather

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrentWeatherService {


    @GET("weather?q={city name}&units=metric&lang={lang}")
    suspend fun getTodayWeatherByCityName(
        @Path("city_name") cityName: String,
        @Path("units") units: String?,
        @Path("lang") lang: String,
    ): CurrentWeatherResponse

    @GET("weather?lat={lat}&lon={lon}&units=metric&lang={lang}")
    suspend fun getTodayWeatherByGeoCoordinates(
        @Path("lat") lat: String,
        @Path("lon") lon: String,
        @Path("units") units: String?,
        @Path("lang") lang: String,
    ): CurrentWeatherResponse

}