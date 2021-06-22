package com.zaich.weathernewyork

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * membuat  SERVICE sebagai titik akhir web API
 * pada api.openweather.org/data/2.5/weather
 * dengan mengambil akun pada lokasi (q) dan token ( token )
 */

interface OpenWeatherInterface {
    @GET("weather")

    //mengambil data web API berdasarkan lokasi dan API Key
    fun getWeather(
        @Query("q") location: String,
        @Query("appid") token: String
    ):retrofit2.Call<OpenWeatherMapResponseData>
}