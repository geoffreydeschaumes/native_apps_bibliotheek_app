package com.example.geoffrey.bibliotheekapp.fragments

import androidx.lifecycle.LifecycleOwner
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.adapter.BookListAdapter
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookListBinding
import com.example.geoffrey.bibliotheekapp.viewModel.BookViewModel
import java.nio.channels.Selector


class BookListFragment : Fragment(){
    private lateinit var binding: FragmentBookListBinding
    private lateinit var bookListViewModel: BookViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false)
        bookListViewModel = ViewModelProviders.of(this).get(BookViewModel::class.java)
        binding.bookListViewModel = bookListViewModel
        val adapter = BookListAdapter()
        binding.bookList.adapter = adapter
        bookListViewModel.bookList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        binding.setLifecycleOwner(this)
        return binding.root
    }
}
