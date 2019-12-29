package com.example.geoffrey.bibliotheekapp.models


import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize


@Entity(tableName="book_table")
@Parcelize
data class Book(
           @PrimaryKey
           @Json(name = "WerkID") val werkId: String,
           @ColumnInfo(name="bkbb_nummer")
           @Json(name = "BKBBNummer") val bKBBNummer: String,
           @ColumnInfo(name = "title")
           @Json(name = "Titel") val titel: String,
           @ColumnInfo(name="edition")
           @Json(name = "Editie") val editie: String,
           @ColumnInfo(name="age")
           @Json(name = "Leeftijd") val leeftijd: String,
           @ColumnInfo(name="isbn")
           @Json(name = "ISBN") val ISBN: String,
           @ColumnInfo(name="age-of_publication")
           @Json(name = "JaarVanUitgave") val jaarVanUitgave: String,
           @ColumnInfo(name="language_publication")
           @Json(name = "TaalPublicatie") val taalPublicatie: String,
           @ColumnInfo(name="sort_material")
           @Json(name = "SoortMateriaal") val soortMateriaal: String
):Parcelable
