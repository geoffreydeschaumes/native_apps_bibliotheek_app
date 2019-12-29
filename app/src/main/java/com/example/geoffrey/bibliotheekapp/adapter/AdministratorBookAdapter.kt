package com.example.geoffrey.bibliotheekapp.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.database.BookDatabase
import com.example.geoffrey.bibliotheekapp.databinding.AdministratorBookViewModelBinding
import com.example.geoffrey.bibliotheekapp.databinding.FragmentBookListBinding
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.models.ShopList
import com.example.geoffrey.bibliotheekapp.utils.AdministratorBookViewHolder
import com.example.geoffrey.bibliotheekapp.utils.AdministratorUserBookViewHolder
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel
import com.example.geoffrey.bibliotheekapp.viewModel.BookViewModel
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class AdministratorBookAdapter(groups: List<ShopList>?, private val application:Application) :
    ExpandableRecyclerViewAdapter<AdministratorUserBookViewHolder, AdministratorBookViewHolder>(groups) {
    override fun onCreateGroupViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): AdministratorUserBookViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_administrator_overview, parent, false)
        return AdministratorUserBookViewHolder(view)
    }

    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): AdministratorBookViewHolder {
        val layoutInference = LayoutInflater.from(parent!!.context)
        val bookBinding: AdministratorBookViewModelBinding = DataBindingUtil.inflate(layoutInference, R.layout.administrator_expandable_book, parent, false)
        return AdministratorBookViewHolder(bookBinding)
    }

    override fun onBindChildViewHolder(
        holder: AdministratorBookViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val x = group!!.items.get(childIndex) as Book
        val book = BookDetailsViewModel(BookDatabase.getInstance(application).bookDatabaseDao, application)
        book.bookToBookViewModel(x)
        holder!!.bind(book)
    }

    override fun onBindGroupViewHolder(
        holder: AdministratorUserBookViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        val shopList = group as ShopList
        holder!!.bind(shopList)
    }
    /*override fun onCreateGroupViewHolder(parent: ViewGroup, viewType: Int): AdministratorUserBookViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_administrator_overview, parent, false)
        return AdministratorUserBookViewHolder(view)
    }


    override fun onCreateChildViewHolder(parent: ViewGroup, viewType: Int): AdministratorBookViewHolder {
        val layoutInference = LayoutInflater.from(parent.context)
        val bookBinding: AdministratorBookViewModelBinding = DataBindingUtil.inflate(layoutInference, R.layout.administrator_expandable_book, parent, false)
        return AdministratorBookViewHolder(bookBinding)
    }

    override fun onBindChildViewHolder(
        holder: AdministratorBookViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>,
        childIndex: Int
    ) {
        val book = BookDetailsViewModel(BookDatabase.getInstance(application).bookDatabaseDao, application)
        holder.bind(book)
    }

    override fun onBindGroupViewHolder(
        holder: AdministratorUserBookViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        val shopList = group as ShopList
        holder.bind(shopList)
    }*/


}

