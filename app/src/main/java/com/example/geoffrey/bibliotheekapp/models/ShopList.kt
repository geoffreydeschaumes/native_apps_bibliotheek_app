package com.example.geoffrey.bibliotheekapp.models

import com.squareup.moshi.Json
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class ShopList (
    @Json(name = "boeken") val userBook:List<Book>,
    @Json(name = "user") val user:User
                ): ExpandableGroup<Book>(user.username, userBook)