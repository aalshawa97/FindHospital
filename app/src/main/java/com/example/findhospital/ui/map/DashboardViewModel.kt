package com.example.findhospital.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "providenceStVincent = LatLng(45.46, -122.792)"
    }
    val text: LiveData<String> = _text
}