package com.example.findhospital.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HospitalListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "These are the hospitals: \"Providence Brigeport, Providence Mercantile, Providence St. Vincent\", Providence Newberg"
    }
    val text: LiveData<String> = _text
}