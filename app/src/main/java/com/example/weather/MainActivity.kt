package com.example.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.models.Weather
import com.example.weather.retrofit.RetrofitClient
import com.example.weather.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var binding: ActivityMainBinding
    lateinit var retrofitService: RetrofitService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val lait = intent.extras!!.getDouble("lat")
        val longit = intent.extras!!.getDouble("long")

        Log.d(TAG, "onCreate: lat:${lait},long:$longit")

        RetrofitClient.apiService.getListWeather(lait,longit,"1fdf5bc5ba7ccbe8ba13c25244b0ef01").enqueue(object:
           Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                if (response.isSuccessful){
                    val data = response.body()
                    changeTemp("293.76")
                    Log.d(TAG, "onResponse: ${data}")
                }
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }

        })




    }

    private fun changeTemp(x: String): String? {
        val celsius = x.toDouble() - 273.0
        val i = celsius.toInt()
        Log.d(TAG, "changeTemp: $i")
        return i.toString()
    }
}