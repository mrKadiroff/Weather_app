package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.livedata.MainViewModel
import com.example.weather.models.Weather
import com.example.weather.modelsphoto.Photo
import com.example.weather.photoRetrofit.Common
import com.example.weather.photoRetrofit.RetrofitService2
import com.example.weather.retrofit.RetrofitClient
import com.example.weather.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var retrofitService: RetrofitService2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        retrofitService = Common.retrofitService

        val lait = intent.extras!!.getDouble("lat")
        val longit = intent.extras!!.getDouble("long")

//        Log.d(TAG, "onCreate: lat:${lait},long:$longit")
        mainViewModel = ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
        mainViewModel.getWeather(lait,longit).observe(this, Observer {
//            Log.d(TAG, "onCreate: ${it.weather[0].description}")
            changeTemp("${it.main.temp}")
            Log.d(TAG, "onCreate: $it")
            binding.hometown.text = it.name
            binding.wind.text = "Shamol tezligi: ${it.wind.speed} m/s"
            binding.humitidy.text = "Namlik: ${it.main.humidity}"
            binding.tiniq.text = "${it.weather[0].description}"
            
            retrofitService.getListWallpaper("${it.weather[0].description}","WcqS56m2RFo3mxnrvePBlcbU2uvVUrBEk0et1HYLapQ").enqueue(object:
                Callback<Photo> {
                override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val wallpaper =data?.results?.get(0)?.urls?.small
                        Log.d(TAG, "onResponse: ${data?.results?.get(0)?.urls?.small}")
                        Glide.with(this@MainActivity).load(wallpaper).into(binding.rasm);
                    }
                }

                override fun onFailure(call: Call<Photo>, t: Throwable) {

                }


            })

        })


        






    }

    private fun changeTemp(x: String): String? {
        val celsius = x.toDouble() - 273.0
        val i = celsius.toInt()
        binding.gradus.text = "${i}°"
        binding.Daraja.text = "Hozir:${i}°"
        Log.d(TAG, "changeTemp: $i")
        return i.toString()
    }
}