package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.geoffrey.bibliotheekapp.activities.prefs
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.repositories.BookRepository
import kotlinx.coroutines.*

class ReservationListViewModel(val database: BookDatabaseDao, application:Application):AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val bookRepository = BookRepository(BookDatabase.getInstance(application))
    var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    init {
        getReservationList()
    }

    private fun getReservationList(){
        coroutineScope.launch {
           _bookList.value =  bookRepository.getBooksList()
        }
    }

     fun saveReservationsToDatabase(){
         Log.d("pref", prefs!!.getCurrentUser())
         var token = " Bearer " + prefs!!.getCurrentUser().substring(10)
         token = token.removeRange(token.length - 2, token.length)
         Log.d("token", token)
        coroutineScope.launch {
            bookRepository.saveReservationsToDatabase(_bookList.value, token)
        }
    }
    fun removeBookById(workId:String) {
        coroutineScope.launch {
                bookRepository.removeBook(workId)
            _bookList.value = bookRepository.getBooksList()
        }
    }

}