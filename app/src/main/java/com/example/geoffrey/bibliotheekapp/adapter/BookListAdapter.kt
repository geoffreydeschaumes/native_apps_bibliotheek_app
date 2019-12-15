package com.example.geoffrey.bibliotheekapp.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.utils.itemViewHolder

class BookListAdapter: RecyclerView.Adapter<itemViewHolder>() {
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
            bundle.putString("title", item.titel)
            bundle.putString("bKBBNumber", item.bKBBNummer)
            bundle.putString("yearOfPublication", item.jaarVanUitgave)
            bundle.putString("sortMaterial", item.soortMateriaal)
            bundle.putString("languagePublication", item.taalPublicatie)
            bundle.putString("edition", item.editie)
            bundle.putString("age", item.leeftijd)
            bundle.putString("isbn", item.ISBN)
            it.findNavController().navigate(R.id.action_bookListFragment_to_bookDetailsFragment, bundle)
        }
    }

    var data = listOf<Book>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


}