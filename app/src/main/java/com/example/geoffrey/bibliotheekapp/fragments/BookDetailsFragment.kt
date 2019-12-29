package com.example.geoffrey.bibliotheekapp.fragments

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders

import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookDetailsBinding
import com.example.geoffrey.bibliotheekapp.factory.BookDetailsViewModelFactory
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel

class BookDetailsFragment : Fragment() {
    private lateinit var binding: FragmentBookDetailsBinding
    private lateinit var bookDetailsViewModel: BookDetailsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_details, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookDetailsViewModelFactory(dataSource, application)
        bookDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(BookDetailsViewModel::class.java)
        bookDetailsViewModel._workId.value = arguments?.getString("workId")
        binding.bookDetailsViewModel = bookDetailsViewModel
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(bookDetailsViewModel.getBookIsReservated()) {
            bookDetailsViewModel._reservateBtnText.value = "Remove from reservations"
        }
        else {
            bookDetailsViewModel._reservateBtnText.value = "Add to reservations"
        }
    }
}
