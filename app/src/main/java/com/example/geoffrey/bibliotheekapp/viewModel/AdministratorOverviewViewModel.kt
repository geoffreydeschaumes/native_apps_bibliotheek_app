package com.example.geoffrey.bibliotheekapp.viewModel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geoffrey.bibliotheekapp.fragments.AdministratorOverviewFragment
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.models.ExpandableShopList
import com.example.geoffrey.bibliotheekapp.models.ShopList
import com.example.geoffrey.bibliotheekapp.network.BookApi
import com.example.geoffrey.bibliotheekapp.repositories.AdministratorRepository
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdministratorOverviewViewModel:ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val administratorRepository = AdministratorRepository()
    var _shopList = MutableLiveData<ArrayList<ShopList>>()
    val shopList: LiveData<ArrayList<ShopList>>
        get() = _shopList

    /**
     * Create a coroutine (non-blocking, asynchronous)
     * calls the suspended function getShopList from the administratorRepository
     * add the books to the shopList
     * and calls the fragment setList to update the adapter
     */
    fun getShopList(fragment:AdministratorOverviewFragment){
        _shopList.value = arrayListOf()
        coroutineScope.launch {
             try {
                 val refObjects = administratorRepository.getShopList()
                 for(shopList in refObjects){
                     val user = administratorRepository.getUserByObjectId(shopList.user)
                     var reservatedBooks = arrayListOf<Book>()
                     for (reservatedBookReference in shopList.userBook) {
                         val book = administratorRepository.getBookByObjectId(reservatedBookReference)
                         reservatedBooks.add(book)
                     }
                     val shopList = ShopList(reservatedBooks, user)
                     _shopList.value!!.add(shopList)
                 }
                 fragment.setList()
             } catch (e: Exception) {
                 Log.d("adminOverviewViewModel:", e.message)
            }
         }
    }
    }