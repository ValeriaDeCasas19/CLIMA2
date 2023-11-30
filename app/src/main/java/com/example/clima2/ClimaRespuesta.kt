package com.example.clima2

class ClimaRespuesta (
    val name: String,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds

)

class Main(
    val temp: Double,
    val temp_min:Double,
    val temp_max: Double,
    val feels_like: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int

)
class Clouds(
    val all: Int
)


class Weather(
    val description: String,
    val icon: String
)