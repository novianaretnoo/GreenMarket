package com.example.greenmarket

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class pesan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan)

        val intentImageButtonHome : ImageButton = findViewById(R.id.imgbutton1hlmnesan)
        intentImageButtonHome.setOnClickListener{ViewHome()}

        val intentImageButtonExplore : ImageButton = findViewById(R.id.imgbutton2hlmnpesan)
        intentImageButtonExplore.setOnClickListener{ViewExplore()}

        val intentImageButtonProfile : ImageButton = findViewById(R.id.imgbutton4hlmnpesan)
        intentImageButtonProfile.setOnClickListener{ViewProfile()}


    }

    private fun ViewProfile() {
        val intent = Intent(this, profile::class.java)
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