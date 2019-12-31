package com.example.geoffrey.bibliotheekapp.fragments

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import androidx.databinding.DataBindingUtil
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.MainActivity
import com.example.geoffrey.bibliotheekapp.adapter.BookListAdapter
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookListBinding
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.viewModel.BookViewModel


class BookListFragment : MenuBaseFragment(){
    private lateinit var binding: FragmentBookListBinding
    private lateinit var bookListViewModel: BookViewModel
    private lateinit var adapter:BookListAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false)
        val application = requireNotNull(this.activity).application
        bookListViewModel = ViewModelProviders.of(this, BookViewModel.Factory(application)).get(BookViewModel::class.java)
        binding.bookListViewModel = bookListViewModel
        adapter = BookListAdapter()
        binding.bookList.adapter = adapter
        fillAdapter(bookListViewModel.bookList)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.search_list, menu)
        var searchManager = getMainActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.searchFilter)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getMainActivity().componentName))

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                bookListViewModel.filterBookListByTitleOrSortMaterial(newText)
                fillAdapter(bookListViewModel.filteredBookList as LiveData<List<Book>>)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
        })
    }

    private fun getMainActivity():Activity{
        return activity as MainActivity
    }

    private fun fillAdapter(bookList: LiveData<List<Book>>) {
        bookList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
    }



}
