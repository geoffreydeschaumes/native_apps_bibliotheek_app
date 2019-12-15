package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.network.BookApi
import kotlinx.android.synthetic.main.fragment_book_details.view.*
import kotlinx.coroutines.*

class BookDetailsViewModel(val database: BookDatabaseDao, application: Application): AndroidViewModel(application){//AndroidViewModel(/*application*/) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private var book = Book("","", "", "", "", "", "", "", "")
    private var books = listOf<Book>()
    var _workId = MutableLiveData<String>()
    val workId : LiveData<String>
        get() = _workId


    var _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() = _title


    var _BKBBNummer = MutableLiveData<String>()
    val BKBBNUMMER: LiveData<String>
        get() = _BKBBNummer


    var _edition = MutableLiveData<String>()
    val edition: LiveData<String>
        get() = _edition


    var _age = MutableLiveData<String>()
    val age: LiveData<String>
        get() = _age


    var _ISBN = MutableLiveData<String>()
    val ISBN: LiveData<String>
        get() = _ISBN


    var _yearOfPublication = MutableLiveData<String>()
    val yearOfPublication: LiveData<String>
        get() = _yearOfPublication


    var _languagePublication = MutableLiveData<String>()
    val languagePublication: LiveData<String>
        get() = _languagePublication


    var _sortMaterial = MutableLiveData<String>()
    val sortMaterial: LiveData<String>
        get() = _sortMaterial

    var _reservateBtnText = MutableLiveData<String>()
    val reservateBtnText: LiveData<String>
        get() = _reservateBtnText

    init {
        getBookById()
       /*coroutineScope.launch {
            temporary()
        }*/
       /* if(getBookIsReservated()) {
            _reservateBtnText.value = "Remove from reservations"
        }
        else {
            _reservateBtnText.value = "Add to reservations"
        }
        */
    }
    private suspend fun temporary(){
        return withContext(Dispatchers.IO){
            database.remove()
        }
    }
    private fun getBookById() {
        coroutineScope.launch {
            val bookById = BookApi.retrofitService.getBookById(_workId.value)
            try {
                 book = bookById.await()
                _workId.value = book.werkId
                _title.value = book.titel
                _BKBBNummer.value = book.bKBBNummer
                _ISBN.value = book.bKBBNummer
                _age.value = book.leeftijd
                _edition.value = book.editie
                _languagePublication.value = book.taalPublicatie
                _sortMaterial.value = book.soortMateriaal
                _yearOfPublication.value = book.jaarVanUitgave
            }
            catch (e:Exception) {
                Log.d("error:", "Boek kon niet bij id worden opgehaald.")
                Log.d("errorMessage", e.message)
            }
        }
    }
    fun saveToReservations (view:View){
        coroutineScope.launch {
            saveReservationsInLocalDatabase(view)
        }

    }
    private suspend fun saveReservationsInLocalDatabase(view:View) {
        val control = getBookIsReservated()
        return if(!control) {
            withContext(Dispatchers.IO) {
                database.insert(book)
                _reservateBtnText.postValue("Remove from reservations")
                getBooksList()
            }
        } else {
            withContext(Dispatchers.IO) {
                database.removeBook(book.werkId)
                _reservateBtnText.postValue("Add to reservations")
                getBooksList()

        }
           // view.findNavController().navigate(R.id.action_bookDetailsFragment_to_reservationListFragment)
        }
    }

    fun getBookIsReservated(): Boolean {

        coroutineScope.launch {
            getBooksList()
        }
        Log.d("bookSize", books?.size.toString())
        val booksLength = books?.size
        if(booksLength != 0){
            for(reservatedBook in books) {
                if (reservatedBook == book) {
                    return true
                }
            }
        }
        return false
    }

    private suspend fun getBooksList() {
        return withContext(Dispatchers.IO) {
            books = database.getBooks()
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
