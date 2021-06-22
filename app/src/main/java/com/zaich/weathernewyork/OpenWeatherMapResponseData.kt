package com.zaich.weathernewyork

import com.squareup.moshi.Json

// membuat model untuk menanggapi web API
data class OpenWeatherMapResponseData(

    //gunakan JSON untuk memaping nama API ke nama lokasi pada aplikasi
    @field:Json(name = "main")
    val locationName: String,
    val weather: List<OpenWeatherMapWeatherData>
)
