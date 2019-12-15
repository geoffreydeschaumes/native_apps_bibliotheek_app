package com.example.geoffrey.bibliotheekapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoffrey.bibliotheekapp.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_book_list)
    }
}
