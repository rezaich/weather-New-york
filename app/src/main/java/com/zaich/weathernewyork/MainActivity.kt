package com.zaich.weathernewyork

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    //Buat koneksi ke XML
    private val titleView: TextView by lazy { findViewById(R.id.mainTitle) }
    private val statusView: TextView by lazy { findViewById(R.id.mainStatus) }
    private val descView: TextView by lazy { findViewById(R.id.mainDesc) }
    private val weatherIconView: ImageView by lazy { findViewById(R.id.mainWeatherIcon) }

    // buat koneksi (instan) ke web API dan mengkonversi gambar dengan Moshi

    private val retrofit by lazy {
        Retrofit.Builder()

            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    //buat koneksi ke layanan web API
    private val weatherApiService by lazy {
        retrofit.create(OpenWeatherInterface::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //memanggil layanan web API
        weatherApiService
        /**
         * Input kata dan api key(Token) dari web API
         * Lakukan registrasi untuk mendapatkan API key
         * di https://home.openweather.org/users/sign_up
         */
            .getWeather("Bekasi","d4638480bbed8a2ead4e0ce9eaa2a297")
            .enqueue(object : Callback<OpenWeatherMapResponseData> {
                override fun onFailure(
                    call: Call<OpenWeatherMapResponseData>,
                    t: Throwable
                ) {
                    showError("Response failed: ${t.message}")
                }

                override fun onResponse(
                    call: Call<OpenWeatherMapResponseData>,
                    response: Response<OpenWeatherMapResponseData>
                ) = handleResponse(response)
            })
    }

    //penanganan kemungkinan hasil kegagalan yang berbeda dalm membuat permintaan
    private fun handleResponse(response: Response<OpenWeatherMapResponseData>)=
        if(response.isSuccessful) {
            response.body()?.let { validResponse ->
                handleValidResponse(validResponse)
            } ?: Unit
        }else{
            showError("Response was unsuccessfull: ${response.errorBody()}")
        }

    //mendapatkan data API
    private fun handleValidResponse(response : OpenWeatherMapResponseData){
        //menampilkan data lokasi dari class OpenWeatherMapResponseData
        titleView.text = response.locationName
        //menampilkan data status,deskripsi dan icon
        response.weather.firstOrNull()?.let {weather ->
            statusView.text = weather.status
            descView.text = weather.description
            Glide.with(this)
                .load("http://openweather.org/img/wn/${weather.icon}@2x.png")
                .centerInside()
                .into(weatherIconView)
        }
    }
    private fun showError(message: String){
        Toast.makeText(this, "message", Toast.LENGTH_SHORT).show()
    }
}