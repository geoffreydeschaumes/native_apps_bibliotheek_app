package com.example.geoffrey.bibliotheekapp.fragments

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
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
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookListBinding
import com.example.geoffrey.bibliotheekapp.factory.BookDetailsViewModelFactory
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel
import com.example.geoffrey.bibliotheekapp.viewModel.BookViewModel
import kotlinx.android.synthetic.main.fragment_book_list.view.*


class BookListFragment : MenuBaseFragment(){
    private lateinit var binding: FragmentBookListBinding
    private lateinit var bookListViewModel: BookViewModel
    private lateinit var bookDetailsViewModel:BookDetailsViewModel
    private lateinit var adapter:BookListAdapter
    /**
     *  Called to have the fragent instantiate its user interface view and is called between onCreate(Bundle) and onActivityCreated(Bundle)
     * @property inflater Is sed to inflate any view in the fragment
     * @property container if non-null, this is the parent view that the fragment's UI should be attached to.
     * @property savedInstanceState Bundle: if non-null, this fragment is being re-constructed from a previous saved state as given here
     *
     * @return View? return the View for the fragment's UI, or null
     *
     * creates bookDetailsViewmodel for landscapeView
     * creates bookLIstViewModel
     * and fills the adapter for the recyclerview
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_book_list, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = BookDetailsViewModelFactory(dataSource, application)
        bookDetailsViewModel = ViewModelProviders.of(this, viewModelFactory).get(BookDetailsViewModel::class.java)
        bookListViewModel = ViewModelProviders.of(this, BookViewModel.Factory(application)).get(BookViewModel::class.java)
        binding.bookDetailsViewModel = bookDetailsViewModel
        binding.bookListViewModel = bookListViewModel
        adapter = BookListAdapter()
        binding.bookList.adapter = adapter
        fillAdapter(bookListViewModel.bookList)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    /**
     * @property view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * @property savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here. This value may be null.
     *calls getBooksList from bookDetailsViewModel. Checks if the book already exists in the reservations saved in the local database
     * Sets the buttons on the view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookDetailsViewModel.getBooksList(view, view.btnReservate)
    }

    /**
     * @property menu Defines a Menu, which is a container for menu items
     * @property inflater Returns a MenuInflater with this context.
     */
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
        adapter.bookDetailsViewModel = bookDetailsViewModel
    }



}
