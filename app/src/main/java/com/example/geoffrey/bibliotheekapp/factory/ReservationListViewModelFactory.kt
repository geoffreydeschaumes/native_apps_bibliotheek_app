package com.example.geoffrey.bibliotheekapp.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geoffrey.bibliotheekapp.database.BookDatabaseDao
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel
import com.example.geoffrey.bibliotheekapp.viewModel.ReservationListViewModel

class ReservationListViewModelFactory(private val dataSource: BookDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    @Suppress
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ReservationListViewModel::class.java)) {
            return ReservationListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}