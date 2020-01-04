package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Application
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.network.BookApi
import com.example.geoffrey.bibliotheekapp.repositories.BookRepository
import kotlinx.android.synthetic.main.fragment_book_details.*
import kotlinx.android.synthetic.main.fragment_book_details.view.*
import kotlinx.coroutines.*

class BookDetailsViewModel(val database: BookDatabaseDao, application: Application): AndroidViewModel(application){//AndroidViewModel(/*application*/) {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val bookRepository = BookRepository(BookDatabase.getInstance(application))
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

    var _token = MutableLiveData<String>()
    val token: LiveData<String>
        get() = _token

    init {
        if(_workId.value != "") {
            getBookById()
        }
    }
    private fun getBookById() {
        coroutineScope.launch {
            try {
                 book = bookRepository.getBookById(_workId.value)
                 bookToBookViewModel(book)
            }
            catch (e:Exception) {
                Log.d("error:", "Boek kon niet bij id worden opgehaald.")
                Log.d("errorMessage", e.message)
            }
        }
    }
    fun saveToReservations (view:View, orientationFromTheScreen:String){
        coroutineScope.launch {
            try {
                if (_reservateBtnText.value == "Add book to reservations"){
                    if (book.titel != "") {
                        _token.value = ""
                        bookRepository.insertBook(book)
                    } else {
                        _token.value = "There is nothing selected!"
                    }
                }
                else {
                    bookRepository.removeBook(book.werkId)
                }
            } catch(e: Exception) {
                Log.d("bookDetailsError", e.message)
            }
            if(orientationFromTheScreen == "portrait")
                view.findNavController().navigate(R.id.action_bookDetailsFragment_to_reservationListFragment)
            else
                if(_token.value == "") {
                    view.findNavController()
                        .navigate(R.id.action_bookListFragment_to_reservationListFragment)
                }
        }

    }

    private fun setReservateButton(view:View, background:Int, icon:Int){
        view.btnReservate.background = view.resources.getDrawable(background)
        view.btnReservate.setImageResource(icon)
    }

    fun getBooksList(view:View) {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                var bookList = bookRepository.getBooksList()
                var count = 0
                if (bookList?.size != 0) {
                    for(reservatedbook in bookList)
                    {
                        if(reservatedbook == book) {
                            count++
                            setReservateButton(view, R.drawable.rounded_button_red, R.drawable.ic_remove_icon)
                            _reservateBtnText.value = "Remove from reservations"
                        }
                    }
                    if(count == 0){
                        setReservateButton(view, R.drawable.rounded_button_green, R.drawable.ic_add_icon)
                        _reservateBtnText.value = "Add book to reservations"
                    }
                }
                else {
                    setReservateButton(view, R.drawable.rounded_button_green, R.drawable.ic_add_icon)
                    _reservateBtnText.value = "Add book to reservations"
                }
                books = bookRepository.getBooksList()
            } catch (e: Exception) {
                Log.d("getBooksListInDetailVM", e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun bookToBookViewModel(book:Book){
        this.book = book
        _workId.value = book.werkId
        _title.value = book.titel
        _BKBBNummer.value = book.bKBBNummer
        _ISBN.value = book.bKBBNummer
        _age.value = book.leeftijd
        _edition.value = book.editie
        _languagePublication.value = book.taalPublicatie
        _sortMaterial.value = book.soortMateriaal
        _yearOfPublication.value = book.jaarVanUitgave
        _token.value = ""
    }
}
