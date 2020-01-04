package com.example.geoffrey.bibliotheekapp.viewModel


import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.network.BookApi
import com.example.geoffrey.bibliotheekapp.repositories.BookRepository
import com.example.geoffrey.bibliotheekapp.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class BookViewModel(application:Application): ViewModel() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val bookRepo = BookRepository(BookDatabase.getInstance(application))

    var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
        get() = _bookList

    var _filteredBookList = MutableLiveData<ArrayList<Book>>()
    val filteredBookList: LiveData<ArrayList<Book>>
        get() = _filteredBookList

    init {
        getBooks()
    }

    private fun getBooks() {
        coroutineScope.launch {
            try {
                _bookList.value = bookRepo.getBooks()
            } catch (e: Exception) {
                Log.d("error", e.message)
            }
        }
    }

    fun filterBookListByTitleOrSortMaterial(filterText:String?) {
        _filteredBookList.value = arrayListOf()
        val filterTextLowerCase = filterText?.toLowerCase() + ""
        for(book in _bookList.value.orEmpty())
        {
            if(book.titel.toLowerCase().contains(filterTextLowerCase) || book.soortMateriaal.toLowerCase().contains(filterTextLowerCase) || book.taalPublicatie.toLowerCase().contains(filterTextLowerCase)) {
                _filteredBookList.value?.add(book)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BookViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}