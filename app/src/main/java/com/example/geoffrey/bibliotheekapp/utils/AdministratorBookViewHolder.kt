package com.example.geoffrey.bibliotheekapp.utils

import android.util.Log
import com.example.geoffrey.bibliotheekapp.databinding.AdministratorBookViewModelBinding
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel

class AdministratorBookViewHolder(val bookBinding: AdministratorBookViewModelBinding): ChildViewHolder(bookBinding.root){
    fun bind(bookViewModel: BookDetailsViewModel){
        Log.d("testBookViewModelBrol", bookViewModel.title.toString())
        bookBinding.bookViewModel = bookViewModel
        bookBinding.executePendingBindings()
    }
}