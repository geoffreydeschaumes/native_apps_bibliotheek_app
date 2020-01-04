package com.example.geoffrey.bibliotheekapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.geoffrey.bibliotheekapp.models.Book
import retrofit2.http.GET


@Dao
interface BookDatabaseDao {

    @Insert
    fun insert(book: Book)

    @Query("SELECT * FROM  book_table")
    fun getBooks():List<Book>


    @Query("Delete from book_table where werkId = :werkId")
    fun removeBook(werkId: String)

    @Query ("Delete from book_table")
    fun removeBooks()

}