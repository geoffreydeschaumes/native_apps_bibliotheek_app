package com.example.geoffrey.bibliotheekapp.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.geoffrey.bibliotheekapp.R
import com.example.geoffrey.bibliotheekapp.models.Book
import com.example.geoffrey.bibliotheekapp.utils.itemViewHolder

class ReservationListAdapter: RecyclerView.Adapter<itemViewHolder>() {
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

        }

        var data = listOf<Book>()
            set(value) {
                field = value
                notifyDataSetChanged()
        }
    fun getWorkIdFromItemPosition(position:Int):String{
        val item = data[position]
        return item.werkId
    }
}