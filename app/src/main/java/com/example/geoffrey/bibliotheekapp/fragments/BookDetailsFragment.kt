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
import kotlinx.android.synthetic.main.fragment_book_details.*

class BookDetailsFragment : MenuBaseFragment() {
    private lateinit var binding: FragmentBookDetailsBinding
    private lateinit var bookDetailsViewModel: BookDetailsViewModel
/**
 * Called to have the fragent instantiate its user interface view and is called between onCreate(Bundle) and onActivityCreated(Bundle)
 * @property inflater Is sed to inflate any view in the fragment
 * @property container if non-null, this is the parent view that the fragment's UI should be attached to.
 * @property savedInstanceState Bundle: if non-null, this fragment is being re-constructed from a previous saved state as given here
 *
 * @return View? return the View for the fragment's UI, or null
 * uses application and dataSource to create a BookDetailsViewModel which has variables that bind to the fragment_book_details
 * takes _workId value from Bundle and uses it to get a book in the BookDetailsViewModel
 */
override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
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

    /**
     * @property view The view returned by onCreateView(LayoutInflater, ViewGroup, Bundle)
     * @property savedInstanceState Bundle: If non-null, this fragment is being re-constructed from a previous saved state as given here. This value may be null.
     *
     * calls getBooksList from bookDetailsViewModel. Checks if the book already exists in the reservations saved in the local database
     * Sets the buttons on the view
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bookDetailsViewModel.getBooksList(view, btnReservate)
    }

}
