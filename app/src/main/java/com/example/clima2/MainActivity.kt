package com.example.clima2

import android.health.connect.datatypes.units.Temperature
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var buscar: EditText
    lateinit var btnbus: ImageButton
    lateinit var ciu: TextView
    lateinit var clim: TextView
    lateinit var min: TextView
    lateinit var max: TextView
    lateinit var temperatura: TextView
    lateinit var img: ImageView

    lateinit var pres: TextView
    lateinit var hum: TextView
    lateinit var mar: TextView
    lateinit var sue: TextView
    lateinit var ata: TextView
    lateinit var tempp: TextView

    val apikey = "e8b3ea531378bafe5d74ecda1bf373e9"
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val weatherApiService = retrofit.create(apiServ::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buscar = findViewById(R.id.buscador)
        btnbus = findViewById(R.id.btnbuscar)
        ciu = findViewById(R.id.ciudad)
        clim = findViewById(R.id.climaTit)
        min = findViewById(R.id.tempMin)
        max = findViewById(R.id.tempMax)
        temperatura = findViewById(R.id.temp)
        img = findViewById(R.id.clima_img)


        pres = findViewById(R.id.presion_valor)
        hum = findViewById(R.id.humedad_valor)
        mar = findViewById(R.id.mar_valor)
        sue = findViewById(R.id.suelo_valor)
        ata = findViewById(R.id.atardecer_valor)
        tempp = findViewById(R.id.temp_valor)

        btnbus.setOnClickListener{
            val cityName = buscar.text.toString()
            getWeatherData(cityName)
        }
    }
    private fun getWeatherData(cityName: String){
        val call = weatherApiService.obtenerClima(cityName, apikey)

        call.enqueue(object : Callback<ClimaRespuesta> {
            override fun onResponse(call: Call<ClimaRespuesta>, response: Response<ClimaRespuesta>){
                if(response.isSuccessful){
                    val weatherData = response.body()
                    if(weatherData != null){
                        updateUI(weatherData)
                    }
                }
            }

            override fun onFailure(call: Call<ClimaRespuesta>, t: Throwable){
                Toast.makeText(this@MainActivity, "No existen los datos", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUI(weatherData: ClimaRespuesta){
        val Temperature = weatherData.main.temp
        val humidity = weatherData.main.humidity
        val description= weatherData.weather[0].description
        val temp_max = weatherData.main.temp_max
        val temp_min = weatherData.main.temp_min
        val pressure = weatherData.main.pressure
        val feels_like = weatherData.main.feels_like
        val sea_level = weatherData.main.sea_level
        val grnd_level = weatherData.main.grnd_level
        val all = weatherData.clouds.all
        val iconUrl = "https://openweathermap.org/img/w/${weatherData.weather[0].icon}.png"


        temperatura.text = "${Temperature.toInt()} °C"
        ciu.text = "${weatherData.name}"
        hum.text = "$humidity%"
        clim.text = "$description"
        min.text = "$temp_min °C Min"
        max.text = "$temp_max °C Max"
        tempp.text = "$feels_like °C"
        pres.text = "$pressure hPa"
        mar.text = "$sea_level m"
        sue.text = "$grnd_level m"
        ata.text = "$all %"
        val iconImageView: ImageView = findViewById(R.id.clima_img)

        Picasso.get()
            .load(iconUrl)
            .into(iconImageView)

    }
}