package com.zaich.weathernewyork

import com.squareup.moshi.Json

// Membuat model untuk menanggapi web API
data class OpenWeatherMapWeatherData(

    //Gunakan JSon untuk memaping main API ke status pada aplikasi
    @field:Json(name = "main")
    val status: String,
    val description: String,
    val icon: String
)
