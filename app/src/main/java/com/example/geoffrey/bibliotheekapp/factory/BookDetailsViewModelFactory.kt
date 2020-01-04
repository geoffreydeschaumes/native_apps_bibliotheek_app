package com.example.geoffrey.bibliotheekapp.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel

/**
 * @return returns instance of ViewModelProvider.Factory
 * @constructor keeps an instance from the local database and application from the fragment
 *
 */
class BookDetailsViewModelFactory(private val dataSource: BookDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    /**
     * creates a viewmodel from a parameter .class
     */
    @Suppress
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BookDetailsViewModel::class.java)) {
            return BookDetailsViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}