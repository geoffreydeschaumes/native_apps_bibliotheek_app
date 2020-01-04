package com.example.geoffrey.bibliotheekapp.viewModel

import android.app.Application
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.landscapePrefs
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.repositories.BookRepository
import kotlinx.android.synthetic.main.fragment_book_list.view.*
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

    /**
     * calls bookById when _workId.value is not equal to ""
     */
    init {
        if(_workId.value != "") {
            getBookById()
        }
    }

    /**
     * creates a coroutine which calls the suspended function getBookById from the bookRepository
     * which then creates the book variable with bookToBookViewModel
     */
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

    /**
     * creates a coroutine which calls insertBook or removeBook from the BookRepository
     * depending on what the _reservateBtnText.value is
     * Goes to the next screendepending on portrait or landscape from the device
     */
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

    /**
     * Sets the background color and the src from the ImageButton
     */
    private fun setReservateButton(view:View, background:Int, icon:Int, btn:ImageButton){
        btn.background = view.resources.getDrawable(background)
        btn.setImageResource(icon)
    }

    /**
     * Creates a coroutine which calls getBooksList from bookRepository (gets the reservations from the local database)
     * checks if the book already exists and calls the setReservateButton method
     */
    fun getBooksList(view:View, btn:ImageButton) {
        coroutineScope.launch(Dispatchers.Main) {
            try {
                book = bookRepository.getBookById(_workId.value)
                var bookList = bookRepository.getBooksList()
                var count = 0
                if (bookList?.size != 0) {
                    for(reservatedbook in bookList)
                    {
                        if(reservatedbook == book) {
                            count++
                            setReservateButton(view, R.drawable.rounded_button_red, R.drawable.ic_remove_icon, btn)
                            _reservateBtnText.value = "Remove from reservations"
                        }
                    }
                    if(count == 0){
                        setReservateButton(view, R.drawable.rounded_button_green, R.drawable.ic_add_icon, btn)
                        _reservateBtnText.value = "Add book to reservations"
                    }
                }
                else {
                    setReservateButton(view, R.drawable.rounded_button_green, R.drawable.ic_add_icon, btn)
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
