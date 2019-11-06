package com.example.geoffrey.bibliotheekapp.models

import com.squareup.moshi.Json

public class User (@Json(name = "username") val username: String,
                   @Json(name="password") val password: String)