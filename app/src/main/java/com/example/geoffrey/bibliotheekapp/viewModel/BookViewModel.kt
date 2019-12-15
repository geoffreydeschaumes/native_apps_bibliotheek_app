package com.example.geoffrey.bibliotheekapp.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.network.BookApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BookViewModel: ViewModel() {

    var _bookList = MutableLiveData<List<Book>>()
    val bookList: LiveData<List<Book>>
            get() = _bookList

    init {
        getBooks()
    }

    fun getBooks(){
         BookApi.retrofitService.getBooks().enqueue(object:Callback<List<Book>> {
            override fun onFailure(call: Call<List<Book>>, t: Throwable) {
                Log.d("error", t.message)
            }

            override fun onResponse(call: Call<List<Book>>, response: Response<List<Book>>) {
                _bookList.value = response.body()!!
            }

        })
    }
}