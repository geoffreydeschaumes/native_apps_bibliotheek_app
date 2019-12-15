package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.models.Book
import kotlinx.coroutines.*

class ReservationListViewModel(val database: BookDatabaseDao, application:Application):AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    init {
        getReservationList()
    }

    fun getReservationList(){
        coroutineScope.launch {
            getReservationsFromRoomDatabase()
        }
    }
    private suspend fun getReservationsFromRoomDatabase() {
        return withContext(Dispatchers.IO) {
            _bookList.postValue(database.getBooks())
        }
    }
}