package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class notif : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notif)

        val intentImageButtonHome : ImageView = findViewById(R.id.imgbutton1hlmnnotif)
        intentImageButtonHome.setOnClickListener{ViewHome()}

        val intentImageButtonExplore : ImageView = findViewById(R.id.imgbutton2hlmnnotif)
        intentImageButtonExplore.setOnClickListener{ViewExplore()}

        val intentImageButtonProfile : ImageView = findViewById(R.id.imgbutton4hlmnnotif)
        intentImageButtonProfile.setOnClickListener{ViewProfile()}


    }

    private fun ViewProfile() {
        val intent =Intent(this, profile::class.java)
        startActivity(intent)
    }

    private fun ViewExplore() {
        val intent = Intent(this, halaman2::class.java)
        startActivity(intent)
    }

    private fun ViewHome() {
        val intent = Intent(this, halaman1::class.java)
        startActivity(intent)
    }
}