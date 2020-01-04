package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.lifecycle.*
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.activities.prefs
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.repositories.BookRepository
import kotlinx.coroutines.*
import com.example.geoffrey.bibliotheekapp.R

class ReservationListViewModel(val database: BookDatabaseDao, application:Application):AndroidViewModel(application) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val bookRepository = BookRepository(BookDatabase.getInstance(application))
    var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    /**
     * calls getReservationList
     */
    init {
        getReservationList()
    }

    /**
     * Creates a coroutine which calls getBooksList from the bookRepository (reservations that a User has made)
     */
    private fun getReservationList(){
        coroutineScope.launch {
           _bookList.value =  bookRepository.getBooksList()
        }
    }

    /**
     * saves the resrvation that is made to the database when there is clicked on save in the reservation_fragment
     * get the token from UserSharedPreference
     * calls saveReservationToDatabase from bookRepository
     */
     fun saveReservationsToDatabase(view:View){
         var token = " Bearer " + prefs!!.getCurrentUser().substring(10)
         token = token.removeRange(token.length - 2, token.length)
        coroutineScope.launch {
            bookRepository.saveReservationsToDatabase(_bookList.value, token)
            bookRepository.removeBooks()
        }
        view.findNavController().navigate(R.id.action_reservationListFragment_to_bookListFragment)
        Toast.makeText(view.context, "Reservations are saved!", Toast.LENGTH_LONG).show()
    }
    fun removeBookById(workId:String) {
        coroutineScope.launch {
                bookRepository.removeBook(workId)
            _bookList.value = bookRepository.getBooksList()
        }
    }

}