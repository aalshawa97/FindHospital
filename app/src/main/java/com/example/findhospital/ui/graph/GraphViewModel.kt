package com.example.findhospital.ui.graph
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class GraphViewModel  : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is the graph Fragment"
    }
    val text: LiveData<String> = _text
}