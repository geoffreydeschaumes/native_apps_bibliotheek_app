package com.example.geoffrey.bibliotheekapp.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.adapter.ReservationListAdapter
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookListBinding
import com.example.geoffrey.bibliotheekapp.databinding.FragmentReservationListBinding
import com.example.geoffrey.bibliotheekapp.factory.BookDetailsViewModelFactory
import com.example.geoffrey.bibliotheekapp.factory.ReservationListViewModelFactory
import com.example.geoffrey.bibliotheekapp.viewModel.ReservationListViewModel

/**
 * A simple [Fragment] subclass.
 */
class ReservationListFragment : Fragment() {
    private lateinit var binding: FragmentReservationListBinding
    private lateinit var reservationListViewModel: ReservationListViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_list, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = BookDatabase.getInstance(application).bookDatabaseDao
        val viewModelFactory = ReservationListViewModelFactory(dataSource, application)
        reservationListViewModel = ViewModelProviders.of(this, viewModelFactory).get(ReservationListViewModel::class.java)
        binding.reservationListViewModel = reservationListViewModel
        val adapter = ReservationListAdapter()
        binding.bookList.adapter = adapter
        reservationListViewModel.bookList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        binding.setLifecycleOwner(this)
        return binding.root
    }




}
