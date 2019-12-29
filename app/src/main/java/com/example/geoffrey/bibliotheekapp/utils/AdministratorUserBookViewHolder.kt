package com.example.geoffrey.bibliotheekapp.utils

import android.view.View
import com.example.geoffrey.bibliotheekapp.models.ShopList
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import kotlinx.android.synthetic.main.fragment_administrator_overview.view.*
import kotlinx.android.synthetic.main.fragment_registration.view.*
import kotlinx.android.synthetic.main.fragment_registration.view.txtUsername

class AdministratorUserBookViewHolder(itemView: View) : GroupViewHolder(itemView) {
    var txt_username = itemView.txtUsername
    var txt_countReservations = itemView.txtCountReservations
    fun bind(shopList: ShopList){
        txt_username.text = shopList.user.username
        txt_countReservations.text = shopList.userBook.size.toString()
    }
}