package com.example.geoffrey.bibliotheekapp.models

import com.squareup.moshi.Json

class ExpandableShopList(
    @Json(name = "boeken") val userBook:List<String>,
    @Json(name = "user") val user:String
)