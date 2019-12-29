package com.example.geoffrey.bibliotheekapp.repositories

import android.util.Log
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.models.ExpandableShopList
import com.example.geoffrey.bibliotheekapp.models.ShopList
import com.example.geoffrey.bibliotheekapp.models.User
import com.example.geoffrey.bibliotheekapp.network.BookApi

class AdministratorRepository {
    suspend fun getShopList():List<ExpandableShopList>{
       return BookApi.retrofitService.getShopList().await()
    }
    suspend fun getBookByObjectId(id:String):Book{
        return BookApi.retrofitService.getBookByObjectId(id).await()
    }
    suspend fun getUserByObjectId(id:String):User {
        return BookApi.retrofitService.getUserByObjectId(id).await()
    }
}