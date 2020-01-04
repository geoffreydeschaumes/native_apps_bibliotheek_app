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

/**
 * AdministratorBookAdapter is the adapter for a ShopList where the administrator can see the reservated books for each user
 *
 * @constructor initializes groups which is the list that is going to be showed on the screen. The application is used to create the bookDatabase in the bookDetailsViewModel
 *
 */
class AdministratorBookAdapter(groups: List<ShopList>?, private val application:Application) :
    ExpandableRecyclerViewAdapter<AdministratorUserBookViewHolder, AdministratorBookViewHolder>(groups) {
    /**
     * calls the view that's going to be showed on the screen (fragment_administrator_overview)
     * This is an implementation of Adapter.onCreateGroupViewholder
     * @property parent is the ViewGroup into whicht the new view will be added after it is bound to an adapter position
     * @property viewType The view type of the new view
     * @return a new AdministratoruserBookViewholder that holds a view of the given viewType
     */
    override fun onCreateGroupViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): AdministratorUserBookViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.fragment_administrator_overview, parent, false)
        return AdministratorUserBookViewHolder(view)
    }

    /**
     * calls the view that's going to be showed on the screen (administrator_expandable_book)
     * This is an implementation of Adapter.onCreateChildViewholder
     * @property parent is the ViewGroup into which the new view will be added after it is bound to an adapter position
     * @property viewType The view type of the new view
     * @return a new AdministratorBookViewholder that holds a view of the given viewType
     */
    override fun onCreateChildViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): AdministratorBookViewHolder {
        val layoutInference = LayoutInflater.from(parent!!.context)
        val bookBinding: AdministratorBookViewModelBinding = DataBindingUtil.inflate(layoutInference, R.layout.administrator_expandable_book, parent, false)
        return AdministratorBookViewHolder(bookBinding)
    }

    /**
     * Takes the selected book Item and creates the bookDetailsViewModel
     * Sets the book variable from bookDetailsViewModel and binds bookDetailsViewModel to the holder
     *
     * @param holder ChildViewholder to bind data
     * @param flatPosition the index in the list where the holder has to bind
     * @param group is the shopList passed to this class which returns each reservated book for each user
     * @param childIndex is the index of the child inside the group
     */
    override fun onBindChildViewHolder(
        holder: AdministratorBookViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val  book= group!!.items[childIndex] as Book
        val bookDetailsViewModel = BookDetailsViewModel(BookDatabase.getInstance(application).bookDatabaseDao, application)
        bookDetailsViewModel.bookToBookViewModel(book)
        holder!!.bind(bookDetailsViewModel)
    }

    /**
     * Takes the selected shopList Item and binds the item to the holder
     *
     * @param holder AdministratoruserBookViewHolder to bind data
     * @param flatPosition the index in the list where the holder has to bind
     * @param group is the shopList passed to this class which returns each reservated book for each user
     */
    override fun onBindGroupViewHolder(
        holder: AdministratorUserBookViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?
    ) {
        val shopList = group as ShopList
        holder!!.bind(shopList)
    }
}

