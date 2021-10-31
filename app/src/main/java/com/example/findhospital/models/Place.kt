package com.example.findhospital.models

import java.io.Serializable

data class Place(var title: String, var description: String, var latitude: Double, var longitude: Double) : Serializable{
}