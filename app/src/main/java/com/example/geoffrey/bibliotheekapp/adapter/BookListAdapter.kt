package com.example.geoffrey.bibliotheekapp.adapter


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.activities.landscapePrefs
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.utils.itemViewHolder
import com.example.geoffrey.bibliotheekapp.viewModel.BookDetailsViewModel
import kotlinx.android.synthetic.main.fragment_book_list.view.*

class BookListAdapter: RecyclerView.Adapter<itemViewHolder>() {
    lateinit var bookDetailsViewModel: BookDetailsViewModel
    /**
     * Implements Adapter.onCreateViewHolder(ViewGroup, Int)
     * @return ItemViewHolder(view) calls the itemViewHolder where the data is binded to the textfields in the view
     * @param parent The ViewGroup into which the new view will be added after it is bound to an adapter position.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_book_item_view, parent, false)
        return itemViewHolder(view)
    }

    /**
     * @return The size from the bookList = 500 in total
     */
    override fun getItemCount() = data.size

    /**
     * Implementation of Adapter.onBindViewholder(itemViewholder, Int)
     * Gets the item from the bookList that is selected and binds the holder
     *
     * Also saves the workId in a Bundle that's going to be passed to another fragment
     * Or fills the books in bookDetailsViewModel when the orientation of the screen = landscape
     */
    override fun onBindViewHolder(holder: itemViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.titel
        holder.sortMaterial.text = item.soortMateriaal
        holder.languagePublication.text = item.taalPublicatie

        holder.itemView.setOnClickListener{
           val bundle = Bundle()
            bundle.putString("workId", item.werkId)

            if(!landscapePrefs.getLandscapeOrientation()) {
                it.findNavController()
                    .navigate(R.id.action_bookListFragment_to_bookDetailsFragment, bundle)
            }
            else {
                bookDetailsViewModel.bookToBookViewModel(item)
            }
        }
    }

    /**
     * @param data is a List of books
     * setter makes it possible to set this list
     */
    var data = listOf<Book>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
}