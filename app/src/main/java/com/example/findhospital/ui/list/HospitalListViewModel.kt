package com.example.findhospital.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HospitalListViewModel : ViewModel() {

   private var name : String = "";
   private var latitude : Double = 0.0;
   private var longitude : Double = 0.0;

      fun HospitalListViewModel(aName: String, aLatitude : Double, aLongitude: Double)
      {
          name = aName
          latitude = aLatitude
          longitude = aLongitude
      }

    private val _text = MutableLiveData<String>().apply {
        //value = "These are the hospitals: \"Providence Brigeport, Providence Mercantile, Providence St. Vincent\", Providence Newberg"
        value = name + " " + latitude + " " + longitude
    }
    val text: LiveData<String> = _text
}