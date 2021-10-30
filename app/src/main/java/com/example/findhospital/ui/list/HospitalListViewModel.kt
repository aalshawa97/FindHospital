package com.example.findhospital.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HospitalListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is hospital Fragment"
    }
    val text: LiveData<String> = _text
}