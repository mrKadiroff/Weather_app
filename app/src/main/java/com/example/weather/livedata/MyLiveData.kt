package com.example.weather.livedata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MyLiveData {

    private var liveData = MutableLiveData<String>()

    fun set(value: String) {
        liveData.value = value
    }

    fun get(): LiveData<String> {
        return liveData
    }

}