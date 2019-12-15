package com.example.geoffrey.bibliotheekapp.models

/*import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
*/
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName="book_table")
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
)
