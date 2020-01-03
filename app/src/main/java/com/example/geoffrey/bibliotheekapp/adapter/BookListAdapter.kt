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

class BookListAdapter: RecyclerView.Adapter<itemViewHolder>() {
    lateinit var bookDetailsViewModel: BookDetailsViewModel
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_book_item_view, parent, false)
        return itemViewHolder(view)
    }

    override fun getItemCount() = data.size

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
    var data = listOf<Book>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }
}