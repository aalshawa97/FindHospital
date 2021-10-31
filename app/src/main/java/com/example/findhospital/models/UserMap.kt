package com.example.findhospital.models

import android.media.Image
import java.io.Serializable

//Map that the user has created
data class UserMap (val title: String, val places: List<Place>) : Serializable{
}