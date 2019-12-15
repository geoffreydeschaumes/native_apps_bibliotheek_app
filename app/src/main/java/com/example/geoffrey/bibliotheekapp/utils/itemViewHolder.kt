package com.example.geoffrey.bibliotheekapp.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geoffrey.bibliotheekapp.R

class itemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.txtTitle)
    val sortMaterial: TextView = itemView.findViewById(R.id.txtSortMaterial)
    val languagePublication: TextView = itemView.findViewById(R.id.txtLanguagePublication)
}