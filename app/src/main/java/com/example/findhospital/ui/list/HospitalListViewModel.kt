package com.example.findhospital.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HospitalListViewModel : ViewModel() {
    /*
   private val name : String = "";
   private val latitude : double = 0.0;
   private val longitude : double = 0.0;


   fun HospitalListViewModel(var aName: String, var aLatitude : double, var aLongitude: double)
   {
       name = aName
       latitude = aLatitude
       longitude = aLongitude
   }
   */

    private val _text = MutableLiveData<String>().apply {
        value = "These are the hospitals: \"Providence Brigeport, Providence Mercantile, Providence St. Vincent\", Providence Newberg"
        //value = latitude + longitude
    }
    val text: LiveData<String> = _text
}