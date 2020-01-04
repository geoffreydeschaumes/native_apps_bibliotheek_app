package com.example.geoffrey.bibliotheekapp.repositories

import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.network.BookApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class BookRepository(private val database: BookDatabase) {
    suspend fun getBooks() : List<Book> {
        return BookApi.retrofitService.getBooks().await()
    }
    suspend fun getBookById(workId: String?) : Book {
        return BookApi.retrofitService.getBookById(workId).await()
    }
    suspend fun insertBook(book:Book) {
        withContext(Dispatchers.IO) {
            database.bookDatabaseDao.insert(book)
        }
    }
    suspend fun removeBook(bookId:String){
        withContext(Dispatchers.IO) {
            database.bookDatabaseDao.removeBook(bookId)
        }
    }
    suspend fun getBooksList() : List<Book>{
        return withContext(Dispatchers.IO) {
            database.bookDatabaseDao.getBooks()
        }
    }
    suspend fun removeBooks(){
        withContext(Dispatchers.IO) {
            database.bookDatabaseDao.removeBooks()
        }
    }
    suspend fun saveReservationsToDatabase(bookList:List<Book>?, token:String):ResponseBody{
        return BookApi.retrofitService.saveReservationsToDatabase(bookList, token).await()
    }
}