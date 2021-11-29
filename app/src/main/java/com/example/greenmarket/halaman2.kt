package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class halaman2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman2)

        val intentImageButton : ImageButton = findViewById(R.id.imageButton15)
        intentImageButton.setOnClickListener{ViewHome()}

        val intentImageButtonProfile : ImageButton = findViewById(R.id.imageButton18)
        intentImageButtonProfile.setOnClickListener{ViewProfile()}


    }

    private fun ViewProfile() {
        val intent = Intent(this, profile::class.java)
        startActivity(intent)
    }

    private fun ViewHome() {
        val intent = (Intent(this, halaman1::class.java))
        startActivity(intent)
    }


}